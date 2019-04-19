from os import system

if __name__ == "__main__":
    if system("python updatefabric.py") == 2:
        system("gradlew --stop")
        system("gradlew cleanLoomBinaries cleanLoomMappings")
        system("gradlew genSources")
        system("gradlew cleanIdea openIdea")
    input("Press any key to exit.")
