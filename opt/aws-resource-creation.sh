echo "             ________________________________________________"
echo "            /                                                \\"
echo "           |    _________________________________________     |"
echo "           |   |                                         |    |"
echo "           |   |  C:\> THALES MACENA                     |    |"
echo "           |   |  C:\> https://github.com/thalesmacena   |    |"
echo "           |   |  C:\> STARTING AWS RESOURCES...         |    |"
echo "           |   |                                         |    |"
echo "           |   |                                         |    |"
echo "           |   |                                         |    |"
echo "           |   |                                         |    |"
echo "           |   |                                         |    |"
echo "           |   |_________________________________________|    |"
echo "           |                                                  |"
echo "            \_________________________________________________/"
echo "                   \___________________________________/"

echo " "
echo " "

echo "########### Creating SQS ###########"
echo "Creating delivery-service-order-queue"
awslocal sqs create-queue --queue-name=delivery-service-order-queue
echo "Queue delivery-service-order-queue created"
awslocal sqs list-queues 
echo " "
echo "########### Creating S3 ###########"
echo "Creating delivey-image-bucket"
awslocal s3api create-bucket --bucket delivey-image-bucket
awslocal s3api put-bucket-acl --bucket delivey-image-bucket --acl public-read-write
echo "Bucket delivey-image-bucket created"
awslocal s3api list-buckets
echo " "
echo "########### Creating Kinesis ###########"
echo "Creating delivery-service-order-event-stream"
awslocal kinesis create-stream --stream-name delivery-service-order-event-stream --shard-count 4
echo "Stream delivey-service-order-event-stream created"
awslocal kinesis list-streams
