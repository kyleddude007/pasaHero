import rethinkdb as r
from optparse import make_option
from paver.easy import *
from datetime import datetime
import os
import json

options (
    dbhost = 'localhost',
    dbport = 28015,
    dbname = 'pasahero'
)

@task
def dbcreate(options):
    """Create pasahero database and tables"""
    conn = r.connect(options.dbhost, options.dbport)
      
    if options.dbname in r.db_list().run(conn):
        return

    r.db_create(options.dbname).run(conn)
    conn.use(options.dbname)

    r.table_create('users').run(conn)   
    r.table_create('migrations', primary_key='name').run(conn)


def seed_table(conn, table, dirname, relname='seed'):
    data =  json.load(open(os.path.join(dirname, relname, table + '.json')))
    result = r.table(table).insert(data, upsert=True).run(conn)

@task
def dbseed(options):
    """Populate pasahero database with seed data"""
    conn = r.connect(options.dbhost, options.dbport)
    conn.use(options.dbname)
    dirname = os.path.dirname(os.path.abspath(__file__))
    table_dumps = sorted(os.listdir(os.path.join(dirname, 'seed')))
    for dump in table_dumps:
        if dump.endswith('.json'):
            table = dump[:-5]
            seed_table(conn, table, dirname)

@task
def dbdrop(options):
    """Drop pasahero database"""
    conn = r.connect(options.dbhost, options.dbport)
    if options.dbname not in r.db_list().run(conn):
        return
    r.db_drop(options.dbname).run(conn)

@task 
def dbmigrate(options):
    conn = r.connect(options.dbhost, options.dbport)
    conn.use(options.dbname)

    migrations = sorted(os.listdir('migrations'))
    for migration in migrations:
        if migration.endswith('.py'):
            if not r.table('migrations').get(migration).run(conn):
                print('Running migration {}...'.format(migration))
                with open('migrations/{}'.format(migration), 'r') as f:
                    exec(f.read(), dict(dbhost=options.dbhost, dbport=options.dbport, dbname=options.dbname), {})
                r.table('migrations').insert(dict(name=migration)).run(conn)


@task
@cmdopts([
    ('name=', 'n', 'Name of migration')
])
def generate_migration(options):
    """
    Generates a scaffold for a migration
    """
    date_str = datetime.utcnow().strftime('%Y-%m-%d.%H-%M-%S')
    with open('migrations/{}.{}.py'.format(date_str, options.dbname), 'w') as f:
        f.write('''
import rethinkdb as r
conn = r.connect(dbhost, dbport)
conn.use(dbname)
''')