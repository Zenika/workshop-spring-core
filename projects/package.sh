rm -rf ../repository
mkdir ../repository
mvn clean dependency:copy-dependencies -Dmdep.useRepositoryLayout -Dclassifier=sources -Dsilent
mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout -Dsilent
for d in */ ; do
    cp -r "$d"target/dependency/* ../repository
done
mvn clean eclipse:clean eclipse:eclipse -DdownloadSources=true
