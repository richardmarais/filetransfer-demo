# filetransfer-demo
<h2>Description</h2>

File transfer demo - uses java sockets to copy files from one location to another.

The source is a directory containing sub directories and files. The client reads from the source and transfers sub directories and files to the server which writes out to the destination. This implementation requires the use of sockets.

<h2>Notes</h2>

1. I would normally use a framework like Spring Boot to wire the project using dependency injection, however the advise is not use any frameworks.
2. This was built using Java 11. You may need to set your JAVA_HOME (e.g. export JAVA_HOME=`/usr/libexec/java_home -v 11`).

<h2>In order to run this application</h2>


1. Alter the <i>root directory</i> property in <i>src/main/resources/<b>application.properties</b></i> (in module <i>filetransfer-demo-server</i>) to where you want the directories and files to be copied to.
2. Start the following Socket servers by running:
    * <i>com.filetransfer.transfer.<b>DirectoryReceiver</b>.main(with no args)</i> in the <i>filetransfer-demo-server module</i>.
    * <i>com.filetransfer.transfer.<b>FileRequestReceiver</b>.main(with no args)</i> in the <i>filetransfer-demo-client</i> module.
3. Call the main method on <i>com.filetransfer.<b>FileTransferClient</b></i>, with an argument representing the root directory that you want processed. 
    * Alternatively, you can call the following Unit test that will process its own set of files and directories. See <i><b>DirectoryReaderTest</b>.java</i> in the <i>filetransfer-demo-client module</i>.
