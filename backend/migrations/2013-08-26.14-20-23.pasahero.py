import rethinkdb as r
conn = r.connect(dbhost, dbport)
conn.use(dbname)

r.table('users').index_create('email').run(conn)
