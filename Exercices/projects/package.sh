rm -rf ../build
mkdir -p ../build/repository
mkdir -p ../build/projects
mvn clean dependency:copy-dependencies -Dmdep.useRepositoryLayout -Dclassifier=sources -q
mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout -q
for d in */ ; do
    cp -r "$d"target/dependency/* ../build/repository
done
mvn clean eclipse:clean eclipse:eclipse -q
cp -r . ../build/projects
mvn clean eclipse:clean -q
find ../build/projects -type f -name '*.iml' -delete
rm -rf ../build/projects/.git
rm -rf ../build/projects/package.sh
rm -rf ../build/projects/.idea
rm -rf ../build/projects/.settings
rm -rf ../build/projects/*/.settings
cp ../settings.xml ../build/
cd ../build/ && zip -r spring-workshop.zip .
