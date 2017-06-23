# Dusty
Dusty - Clean up your fragments with ease

## Introduction
This library will automatically clear your @Clear annotated references inside fragments.

Inspired by this class from a google sample https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedValue.java

## Download
```groovy
dependencies {
	 compile 'com.github.IVIanuu.Dusty:dusty:LATEST-VERSION'
         annotationProcessor 'com.github.IVIanuu.Dusty:dusty-processor:LATEST-VERSION'
}
```
## Usage

In order to make dusty work you have to do two simple things.

First annotate your references that you want to be cleared.

Then in onCreate register the fragment by calling Dusty.register(this);

```java
public class SampleFragment extends Fragment {

    @Clear SampleAdapter sampleAdapter;
    @Clear String title;
    @Clear UpdateHelper updateHelper
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dusty.register(this);
    }
    
    ...
}
```

The values will be automatically cleared in onDestroyView.
