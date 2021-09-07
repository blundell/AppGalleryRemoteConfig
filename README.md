# AppGallery Connect Remote Configuration

This repository shows you how the Remote Configuration feature of AppGallery Connect can be used.

The Android App has one main screen with:
 - an input to change the users "country" user attribute.
 - a background color based on remote configuration
 - a text view based on remote configuration
 - a button for forcing a refresh of the remote configuration

When the app loads it loads the latest remote configuration.
This remote configuration is used to decide the text on the screen and the background color.
The app uses Huawei Analytics to setup a User Attribute for 'country', and Remote Configuration to create a conditional remote toggle.
This condition says, when the user is in the UK the screen changes background color and says "Welcome".

We demonstrate the user of Remote Configuration, Conditions, Analytics, User Attributes and Config Refreshing.

![](app_gallery_remote_configuration.gif)

As explained in the blog here:

https://blog.blundellapps.co.uk/remote-configuration-using-appgallery-connect/

Adding the AppGallery Connect SDK:

https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-get-started-android-0000001058210705

Getting started with Remote Configuration:

https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-remoteconfig-android-getstarted-0000001056347165

Huawei Training on Remote Configuration:

https://developer.huawei.com/consumer/en/training/detail/101601449812686029
