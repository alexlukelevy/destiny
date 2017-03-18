# Destiny Light Loader

Created as an experimental app to get familiar with the Destiny public REST API, this CLI highlights the items to equip in order to optimise your light level.

### Build
```sh
mvn clean install package
```

### Run

```sh
./destiny-light-loader.sh <PSN-GAMERTAG> <PSN-ID>
```

* `<PSN-GAMERTAG>` is your PSN display name e.g. MadSnipez2k17
* `<PSN-ID>` is your PSN associated email account e.g. mad.snipez@email.com

You will also be prompted for your PSN password. This is not stored but is required to make authenticated requests to the Bungie API.