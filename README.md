# Destiny Light Loader

Created as an experimental app to get familiar with the Destiny public REST API, this CLI highlights the items to equip in order to optimise your light level.

```sh
$ lightload LethalLevy --psn-id <PSN-ID>  --psn-pass <PSN-PASS>
```

### Authentication
By default, Light Loader will prompt you for your PSN ID and password every time you run the tool. If you don't want to type out your credentials every time then you can set the `PSN_ID` and `PSN_PASS` environment variables.
