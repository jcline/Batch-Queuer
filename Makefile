
OBJS = \
			 Window.java \
			 Credentials.java \
       BatchUploader.java

BatchUploader:
	javac -Xlint:deprecation -cp ".:libs/*" $(OBJS)
