package org.armitage;

import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.LegacyFacebookClient;

public class App2 {
    public static void main(String[] args) {
        LegacyFacebookClient facebookClient = new DefaultLegacyFacebookClient("263487330650347", "5579b379257ae5cae09409f8e41a2364");
        System.out.println(facebookClient);
    }
}
