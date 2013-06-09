# Creating a (staged) Documentation site for Nazgul Framework: Tools

Maven's [Site Plugin](http://maven.apache.org/plugins/maven-site-plugin/) is a nifty, powerful and rather complex
plugin, which generates much required documentation for Maven reactor builds. The site plugin is configured for
somewhat sensible default values within the Nazgul Tools reactor. These defaults - and how to change them to match
your requirements - are shown below.

> What's a *staged* Documentation site?
>
> A *staged site* is maven vocabulary for documentation aggregated from several projects,
> related to one another by existing within a
> [multi-project maven reactor](http://maven.apache.org/plugins/maven-site-plugin/examples/multimodule.html).
> The Nazgul Framework: Tools and Nazgul Framework: Core are two examples of multi-project
> reactors which contain documentation intended for staging.

## Building the Documentation site

Maven normally builds documentation sites using the standard commands

    mvn site

    mvn site:stage

These commands are assumed to be executed in the top directory of the maven build reactor.
When executed from the Nazgul Tools root directory, these commands produce the staged site
in the directory `/tmp/nazgul_tools/${project.version}`, where `${project.version}` is substituted
for the actual version of the Nazgul Tools reactor.

### Changing the local staging directory

Add the parameter `site.staging.localDirectory` to the stage command to make the staged
site appear in another directory:

    mvn site

    mvn site:stage -Dsite.staging.localDirectory=/another/location

Again, these commands are assumed to be executed in the top directory of the maven build reactor.
When executed from the Nazgul Tools root directory, these commands produce the staged site
in the directory `/another/location/${project.version}`, where `${project.version}` is substituted
for the actual version of the Nazgul Tools reactor.

## Deploying the Documentation site to a remote server

This is a somewhat complex process, since multiple settings including operating-system-specific security details are
combined to generate the possibility to depoy a Documentation site remotely. This is not intended to be a normative
description, but simply to create a step-by-step description (and improve your understanding) for deploying a
staged site to a remote server.

### 0) Assemble server data for remote deployment

To be able to deploy documentation to a remote server, you need access to the server - either 
shell access or through other means. For reference later in the process, the following 
extra parameters should be recorded: 

<table>
    <tr>
        <th>Parameter</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>yourUserName</td>
        <td>The username on the remote server. This is the UID which should own the 
        deployed data, which is normally the same as the remote server 
        operating system account login.</td>
    </tr>
    <tr>
        <td>remoteServer</td>
        <td>The IP or DNS of the remote server.</td>
    </tr>
    <tr>
        <td>pathToSite</td>
        <td>The absolute path to the site on the remote server. Since maven produces
        the site as a set of static HTML pages (and resources, such as images) it
        is recommended to point the pathToSite into the section normally published
        by your webserver.</td>
    </tr>
</table>

**Note!** The [yourUserName] must have write permissions on [pathToSite] on [remoteServer],
in order to permit remote site deployment.

### 2) Make the remote server's SSH daemon recognize your public SSH key

There are other security options than an SSH certificate, as explained in
[http://maven.apache.org/settings.html#Servers](Servers). 
If you use certificate security as illustrated above, ensure that your *public* SSH key is
copied to the target server's SSH daemon's recognitioning file.

For example, on a Linux server this is typically done by appending your public SSH 
key to the ${HOME}/.ssh/authorized_keys file of the account you want to use to deploy 
your staged site.

This process is better described on the net:

1.	**Linux**: http://www.csua.berkeley.edu/~ranga/notes/ssh_nopass.html

2. 	**Mac OS X**: http://xiix.wordpress.com/2007/03/31/how-to-set-up-public-key-authentication-pka-on-your-mac/

3. 	**Windows**: http://comptb.cects.com/1439-openssh-rsa-authentication-for-windows-and-linux

Before progressing, make certain that you can log in to the remote server using your
certificate. This is the authentication process we will configure Maven to use.

### 3) Define a server entry in your ${HOME}/.m2/settings.xml

Define a settings.xml server setting with the id jGuru_NazgulToolsSite:

	<server>
    	<id>jGuru_NazgulToolsSite</id>
        <username>[yourUserName]</username>
        <privateKey>[path to your SSH private key]</privateKey>
        <passphrase>[password for your SSH private key]</passphrase>
        <filePermissions>664</filePermissions>
        <directoryPermissions>755</directoryPermissions>
	</server>

### 4) Build the site documentation and deploy it

The commands below are assumed to be executed in the top directory of 
the maven build reactor. Build the site and stage-deploy (which is a combination of 
staging and deploying) it to a remote server using the commands:

    mvn site

    mvn site:stage-deploy -Dsite.url.prefix=scp://[yourUserName]@[remoteServer]/[pathToSite]
    
where the various parameters are the parameters you defined in step 0 above.
Note that the user [yourUserName] should have write access to the [pathToSite] directory
to permit copying the staged site there.