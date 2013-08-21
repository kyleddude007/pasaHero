# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "precise32"
  config.vm.box_url = "http://files.vagrantup.com/precise32.box"
  config.vm.synced_folder ".", "/home/vagrant/pasahero/", :nfs => true
  config.vm.synced_folder ".", "/var/www/pasahero/", :nfs => true
  config.vm.network :private_network, ip: "172.16.0.13"
  config.vm.network :forwarded_port, guest: 10, host: 11000
  config.vm.network :forwarded_port, guest: 5000, host: 15000
  config.vm.network :forwarded_port, guest: 8080, host: 28000 # RethinkDB management
  config.vm.network :forwarded_port, guest: 1935, host: 11935 # RethinkDB management
end
