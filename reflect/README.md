# Implement RMI from Scratch using Java Reflection

## The logistics

1. Implement a server-side class which implements a interface.
2. Create a `Skeleton` for this server-side class
3. On the client side, create a stub which connects a `Skeleton`. Then you can invoke the remote method through the `Stub`

## Java Reflection

You can get the information of a class in runtime.
### Classes

Get a class from a instance of a class
```java
Class c = obj.getClass()
```

Get a class from a type: use `.class` syntax
```java
Class i = int.class
Class c = com.class.some.class
```

Ge a class from fully-qualified name of a class:
```java
Class c = Class.forName("ccom.class.some")
```

## Class Loader
A class loader is an object that is responsible for loading classes. The class ClassLoader is an abstract class. Given the binary name of a class, a class loader should attempt to locate or generate data that constitutes a definition for the class. A typical strategy is to transform the name into a file name and then read a "class file" of that name from a file system.

For example, an application could create a network class loader to download class files from a server. Sample code might look like:
```java
ClassLoader loader = new NetworkClassLoader(host, port);
Object main = loader.loadClass("Main", true).newInstance();
    . .
```
The network class loader subclass must define the methods findClass and loadClassData to load a class from the network. Once it has downloaded the bytes that make up the class, it should use the method defineClass to create a class instance. A sample implementation is:

```java
class NetworkClassLoader extends ClassLoader {
	String host;
	int port;

	public Class findClass(String name) {
		byte[] b = loadClassData(name);
		return defineClass(name, b, 0, b.length);
	}

	private byte[] loadClassData(String name) {
		// load the class data from the connection
              . . .
	}
}
```
## Proxy objects and invocation handlers

There is interface `InvocationHandler` which has a method `Object invoke(Object proxy, Method method, Object[] args) throws Throwable` needed to be override.

The mechanism with `Proxy` in `Reflect` is to dispatch methods to different objects.

`Proxy` is a abstract class. We can only get a class `Proxy` from a factory which generate a class.
```java
public class GenericProxyFactory {
	public static<T> T getProxy(Class<T> intf, final T obj)
	{
		return (T)		
			Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				new Class[] { intf },
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method,
                      Object[] args) throws Throwable {
                        return method.invoke(obj, args);
                    }
                });
    }
}
```
You can see the `Proxy` object is actually a real object which can invoke method dispatched by `invoke`.

To use this `Proxy Factory`, code like this:
```java
Set s = newLoggingProxy(Set.class, new HashSet());
s.add("three");
if (!s.contains("four"))
	s.add("four");
System.out.println(s);
```

## ObjectInputStream and ObjectOutputStream

You can read out/ write in a object with a inner stream.

## Stub/Skeleton with These Mechanism

On the stub side, you can read out a object with `ObjectOutputStream`, then use `Proxy` to instantiate a proxy object with some interfaces.

Inside the `Proxy` object, there is a `InvocationHandler` which dispatch methods of this object to different methods. And the dispatch rules are defined in the overrode method `invoke`.

For the communication between `Stub` and `Skeleton`. Now, my understanding is following:

1. `Stub` create proxy objects: ClassLoader (comes from full name); Interfaces for the class; Invocation Handler (dispatch methods invoked on this proxy object).
2.  `Stub` and `Skeleton` transmit the proxy objects between each other. The way to transmit them is by using `ObjectInputStream` and `ObjectOutputStream`.
