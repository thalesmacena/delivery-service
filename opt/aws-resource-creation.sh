echo "########### Creating SQS ###########"
echo "Creating delivery-service-order-queue"
awslocal sqs create-queue --queue-name=delivery-service-order-queue
echo "Queue delivery-service-order-queue created"
echo "Creating delivery-service-delete-image-queue"
awslocal sqs create-queue --queue-name=delivery-service-delete-image-queue
echo "Queue delivery-service-delete-image-queue created"
echo " "
echo "########### Creating S3 ###########"
echo "Creating delivery-image-bucket"
awslocal s3api create-bucket --bucket delivery-image-bucket
echo "Set delivery-image-bucket permissions"
awslocal s3api put-bucket-acl --bucket delivery-image-bucket --acl public-read-write
echo "Bucket delivery-image-bucket created"
echo " "
echo "########### Creating Kinesis ###########"
echo "Creating delivery-service-order-event-stream"
awslocal kinesis create-stream --stream-name delivery-service-order-event-stream --shard-count 4
echo "Stream delivery-service-order-event-stream created"
