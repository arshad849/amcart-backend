aws dynamodb create-table \
    --table-name Products \
    --attribute-definitions \
        AttributeName=product_id,AttributeType=S \
    --key-schema \
        AttributeName=product_id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://localhost:8000

aws dynamodb create-table \
    --table-name Counter \
    --attribute-definitions \
        AttributeName=counterName,AttributeType=S \
    --key-schema \
        AttributeName=counterName,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://localhost:8000

aws dynamodb update-table --table-name Products --attribute-definitions AttributeName=label,AttributeType=S --global-secondary-index-updates '[{\"Create\": {\"IndexName\": \"label-index\",\"KeySchema\": [{\"AttributeName\": \"label\",\"KeyType\": \"HASH\"}],\"Projection\": {\"ProjectionType\": \"ALL\"},\"ProvisionedThroughput\": {\"ReadCapacityUnits\": 1,\"WriteCapacityUnits\": 1}}}]' --endpoint-url http://localhost:8000