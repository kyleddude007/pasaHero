from flask import Flask, session, request
import rethinkdb as r
import config

app = Flask(__name__)
app.config.from_object('config')

def connect_db():
    return r.connect(options.dbhost, options.dbport)

@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/ps/api/login', methods=['GET'])
def login():
  print 'a'

if __name__ == '__main__':
    app.run(host='0.0.0.0')