#!/bin/bash
# firefox &
# sleep 5
# kill %1
pkill firefox && echo 'user_pref("network.dnsCacheExpiration", 0)' >> ~/.mozilla/firefox/*.default/prefs.js