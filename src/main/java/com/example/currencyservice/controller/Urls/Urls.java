package com.example.currencyservice.controller.Urls;

public interface Urls {
    String ROOT = "api";

    interface currencies {
        String name = "currencies";

        String full = ROOT + "/" + name;
    }

    interface gif {
        String name = "gif";

        String full = ROOT + "/" + name;

        interface target {
            String name = "{currency}";
            String full = gif.full + "/" + name;
        }
    }
}
