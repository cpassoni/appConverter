mvn clean install
scp -i ~/.ssh/asdf.pem  target/AppConverter-1.0-SNAPSHOT-exec.jar ec2-user@54.88.144.125:~/
#ssh -i ~/.ssh/asdf.pem   ec2-user@54.88.144.125 rdeploy.sh
#cat rdeploy.sh | ssh -i ~/.ssh/asdf.pem   ec2-user@54.88.144.125 $1
