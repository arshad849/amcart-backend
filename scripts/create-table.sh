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