from datetime import timedelta, datetime
from hashlib import sha256
import uuid
from flask import Flask, session, request, redirect, jsonify, json
from flask_login import (LoginManager, login_required, login_user, current_user, logout_user, UserMixin)
from itsdangerous import URLSafeTimedSerializer
import rethinkdb as r
import config
from pytz import timezone
import json


app = Flask(__name__)
app.config.from_object('config')

def connect_db():
    return r.connect(app.config['DBHOST'], app.config['DBPORT'], app.config['DBNAME'])

@app.route('/')
def hello_world():
    return 'Hello World!'

class User():
    """
    User Class for flask-login
    """

    def __init__(self, data):
        self.email = data['email']
        self.id = data['id']
        self.password = data['password']
        self.salt = data['salt']
        self.created_on = data['created_on']
        self.updated_on = data['updated_on']

    def get_auth_token(self):
        data=[str(self.email), self.password]
        return login_serializer.dumps(data)

    def json(self):
        return dict(email=self.email, id=self.id, created_on=self.created_on, updated_on=self.updated_on)

    @staticmethod
    def filter_by_email(email):
        conn = connect_db()
        users = list(r.table('users').filter({'email':email}).run(conn))
        conn.close()
        if len(users) == 1:
            user = users[0]
            return User(user)
        return None

    @staticmethod
    def get(id):
        conn = connect_db()
        user = r.table('users').get(id).run(conn)
        conn.close()
        return User(user)

class Fare():
    """
    Fare class for retreving fares
    """
    def __init__(self, data):
        self.discounted = data['discounted']
        self.distance = data['distance']
        self.distance_unit = data['distance_unit']
        self.id = data['id']
        self.regular = data['regular']
        self.single_journey = data['single_journey']
        self.stored_value = data['stored_value']
        self.type = data['type']

    def json(self):
        return dict(discounted=self.discounted, distance=self.distance, id=self.id, distance_unit=self.distance_unit, regular = self.regular, single_journey=self.single_journey, stored_value=self.stored_value, type=self.type)
    
def get_salt():
    return uuid.uuid4().hex

def hash_password(password, salt):
    return sha256(password+salt).hexdigest()
"""
@login_manager.user_loader
def load_user(userid):
    return User.get(userid)

@login_manager.token_loader
def load_token(token):
    max_age = app.config['REMEMBER_COOKIE_DURATION'].total_seconds()
    data = login_serializer.loads(token, max_age = max_age)
    user = User.get(data[0])
    if user and data[1] == user.password:
        return user
    return None
"""

@app.route('/ps/api/logout')
def logout():
    session.pop('user_id', None)
    return "Logged out", 200

@app.route('/ps/api/login', methods = ['POST'])
def login():
    if request.method == 'POST':
        user = User.filter_by_email(request.json['email'])
        if user and hash_password(request.json['password'], user.salt) == user.password:
            session['user_id'] = user.id
            print "login: "
            print session
            return jsonify(user.json())

@app.route('/ps/api/users/<id>', methods = ['GET'])
def get_user(id):
    user = User.get(id)
    return jsonify(user.json())

@app.route('/ps/api/signup', methods = ['POST'])
def signup():
    conn = connect_db()
    if not r.table('users').get_all(request.json['email'], index='email').is_empty().run(conn):
        return 'Email already exists.', 422
    email = request.json['email']
    salt = get_salt()
    password = hash_password(request.json['password'], salt)
    created_on = str(datetime.now(timezone(app.config['TIMEZONE'])))
    updated_on = created_on
    insert = r.table('users').insert({'email':email, 'password': password, 'salt':salt, 'created_on':created_on, 'updated_on':updated_on}, return_vals=True).run(conn)
    user = User(insert['new_val'])
    conn.close()
    if user is None:
        return 'There was an error creating a new user.', 422 
    session['user_id'] = user.id
    return jsonify(user.json())

@app.errorhandler(404)
def not_found(error=None):
    message = {
            'status': 404,
            'message': 'Not Found: ' + request.url,
    }
    response = jsonify(message)
    response.status_code = 404
    return response

@app.errorhandler(422)
def not_found(error=None):
    message = {
            'status': 422,
            'message': 'Unprocessable entity' + request.url,
    }
    response = jsonify(message)
    resp.status_code = 404
    return response

@app.route('/ps/api/fares/<transit_type>/<distance>', methods=['GET'])
def get_fare(transit_type, distance):
    conn = connect_db()
    data = r.table('fares').filter({'distance': distance, 'type':transit_type}).run(conn)
    conn.close()
    return jsonify({'response':list(data)})

@app.route('/ps/api/pnr/<start>', methods=['GET'])
def get_pnr_fare(start):
    conn = connect_db()
    data = r.table('pnr_fares').filter({'start': start}).run(conn)
    conn.close()
    return jsonify({'response':list(data)})


if __name__ == '__main__':
    app.run(host='0.0.0.0')

