## Welcome to FasterPay Android SDK

FasterPay Android SDK enables you to integrate the FasterPay's Checkout Page seamlessly without having the hassle of integrating everything from Scratch. Once your customer is ready to pay, FasterPay will take care of the payment, notify your system about the payment and return the customer back to your Thank You page.

### Add the SDK to your project

The FasterPay Android SDK is now available at [Maven Repository](https://repo1.maven.org/maven2/com/fasterpay/). The latest version is available via `mavenCentral()`:

```
implementation 'com.fasterpay:fasterpay-android-sdk:1.0'
```

or import it manually

#### Import SDK

1. Add library to your project in either of the following ways
	1. Built binary
		* Download sdk as compiled ".aar" file [here](https://github.com/FasterPay/fasterpay-android/blob/master/built-library/fpsdk.aar)
		* Open your project > **File > New > New Module**
		* Click **Import .JAR/.AAR Package** then click **Next**
		* Enter the location of the compiled AAR or JAR file then click **Finish**
	2. Source
	    * Clone code:
	    ```
	    https://github.com/FasterPay/fasterpay-android.git
	    ```
		* Click **File > New > Import Module**
		* Enter the location of the library module directory the click **Finish**

2. Make sure the library is listed at the top of your settings.gradle file

```
include ':app', ':fpsdk'
```

3. Open the app module's build.gradle file and add a new line to the dependencies block as shown in the following snippet:

```
dependencies {
    implementation project(":fpsdk")
}
```

### Initiating Payment Request

```java
class MainActivity : AppCompatActivity() {

    val fasterPay: FasterPay by lazy {
            FasterPay("<your public key>") 
        }

    private fun requestPayment() {
        val form = fasterPay.form()
            .amount("0.5")
            .currency("USD")
            .description("Golden Ticket")
            .merchantOrderId(UUID.randomUUID().toString())
            .successUrl("<your definition url>")

        startActivity(fasterPay.prepareCheckout(this, form))
    }
}
```

`<your definition url>`: "scheme:host[?queryParameter]"

For example: "com.application.sample://your.app?successId=3122eb"

- `scheme`: with application id
- `host`: define your customize host

This url will be use to redirect to your activity. 
To catch the `successUrl` please open `AndroidManifest.xml` and add following snippet to your activity 

```java
<activity
        android:launchMode="singleInstance">
    ...
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>

        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>

        <data
            android:host="<host in your definition url>"
            android:scheme="<scheme in your definition url>"/>
    </intent-filter>
</activity>
```

then handling the successUrl in `onNewIntent()` method of your activity. For example:

```java
class MainActivity : AppCompatActivity() {

    ...
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        val successId = uri?.getQueryParameter("successId")
        //handle with successId
    }
}
```


### FasterPay Test Mode

FasterPay has a Sandbox environment called Test Mode. Test Mode is a virtual testing environment which is an exact replica of the live FasterPay environment. This allows businesses to integrate and test the payment flow without being in the live environment. Businesses can create a FasterPay account, turn on the **Test Mode** and begin to integrate the widget using the test integration keys.

#### Initiating FasterPay Gateway in Test-Mode

```java
val fasterPay: FasterPay by lazy {
        FasterPay("<your public key>", true)
    }
```

### Questions?

- Common questions are covered in the [FAQ](https://www.fasterpay.com/support).
- For integration and API questions, feel free to reach out Integration Team via [integration@fasterpay.com](integration@fasterpay.com)
- For business support, email us at [merchantsupport@fasterpay.com](merchantsupport@fasterpay.com)
- To contact sales, email [sales@fasterpay.com](sales@fasterpay.com).
