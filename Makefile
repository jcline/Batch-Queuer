
OBJS = \
			 Window.java \
			 Credentials.java \
			 CredentialsDialog.java \
       BatchUploader.java

BatchUploader:
	javac -Xlint:deprecation $(OBJS)
