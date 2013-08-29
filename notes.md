8/28/13
I was going through the Facebook SDK tutorials and kept got this error

`java.lang.NoClassDefFoundError: android.support.v4.content.LocalBroadcastManager`

Not sure if all these steps are necessary but to fix it I had to 
1. Copy the `android-support-v4.jar` in the Facebook SDK libs/ directory into my project directory's own libs/.
2. Configure Java build path of the probject:
  2.1 Add external jar (android-support-v4.jar in project's libs/)
  2.2 In the Order/Export sections, check Android Private Libraries, Android dependencies and android-support-v4.jar

