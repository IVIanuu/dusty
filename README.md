# Dusty
Dusty - Clean up your classes with ease

## Introduction
This library will automatically clear your @Clear annotated references inside classes.

## Download
```groovy
// in your root gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```groovy
// in your module
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
public class MyClass {

    @Clear SampleAdapter sampleAdapter;
    @Clear String title;
    @Clear UpdateHelper updateHelper
    
   
   public void release() {
        Dusty.dust(this); // sets the annotated fields to null
   }
    
    ...
}
```

## License

```
Copyright 2017 Manuel Wrage

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
