package main

import (
  "flag"
  "fmt"
  r "github.com/christopherhesse/rethinkgo"
)

const (
    Host   = "localhost"
    Port = 28015
    Name = "pasahero"
)

func drop() {
  conn, err := r.Connect("localhost:28015", "pasahero")
  if err !=nil {
    fmt.Println("Error connection: ", err)
    return
  }
  if Name not in r.db_list().Run(conn) 
}

func main() {
  drop := flag.Bool("drop", false, "Drop the pasahero database.")
  create := flag.Bool("create", false, "Create the pasahero database.")
  seed := flag.Bool("seed", false, "Populate the pasahero database")
  migrate := flag.Bool("migrate", false, "Run database migrations")

  flag.Parse()

  fmt.Println("create:", *create)
  fmt.Println("drop:", *drop)
  fmt.Println("seed:", *seed)
  fmt.Println("migrate:", *migrate)
}