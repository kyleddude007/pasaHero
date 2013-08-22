import rethinkdb as r
from optparse import make_option
from paver.easy import *

options (
    dbhost = 'localhost',
    dbport = 28015,
    dbname = 'pasahero'
)

@task
def dbcreate(options):
    """Create pasahero database"""
    conn = r.connect(options.dbhost, options.dbport)
      
    if options.dbname in r.db_list().run(conn):
      return

    r.db_create(options.dbname).run(conn)
    conn.use(options.dbname)

    r.table_create('users').run(conn)
    r.table_create('migrations', primary_key='name').run(conn)


@task
def dbseed(options):
    """Populate pasahero database with seed data"""
    conn = r.connect(options.dbhost, options.dbport)
    conn.use(options.dbname)

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
