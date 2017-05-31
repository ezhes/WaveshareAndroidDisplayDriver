
# Waveshare Android Touch Driver

Look, this is really poorly built. I don't recommend using it. This was a bit of software I built to get my 7inch AIO waveshare touch screen LCD combo to work with an android stick I had to build a sort of DIY tablet. All the research credit goes to derekhe and his version for raspbian [here](https://github.com/derekhe/waveshare-7inch-touchscreen-driver).

To use this:

1. Get Xposed installed
2. [Install XInstaller module for Xposed](http://repo.xposed.info/module/com.pyler.xinstaller)
3. In XInstaller options, go to the menu named "Installations" and check the box that says "Checking signatures" and "Verifying apps" 

This disables a lot of very important Android security permissions but it's neccesary to create a userspace touch driver because we need the INJECT_EVENTS permission.

Once you've got that setup, you can just install this project as a normal app. With the display connected over USB you will get a prompt from the system to always open the app with a USB device is attached, select yes and 'always'. Next, launch the app, and then reboot. The application should now autolaunch and your touchscreen might work. Maybe. Good luck, lot's of stuff is hardcoded since I didn't really care. This is really just a PoC and you'll likely need to re-implement this yourself if you actually need it. Not terribly reliable either. 