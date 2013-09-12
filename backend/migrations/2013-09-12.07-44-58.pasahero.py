import rethinkdb as r
conn = r.connect(dbhost, dbport)
conn.use(dbname)

r.table_create('fares').run(conn)
