TERM=$1

curl -XGET 'http://localhost:9200/chigun/issues/_search?pretty' -d " {
  \"_source\" : [\"path\"],
  \"query\" : {
    \"fuzzy\" : {
      \"body\" : {
        \"value\" : \"$TERM\",
        \"boost\" : 1.0
      }
    }
  },

 \"highlight\" : {
	\"pre_tags\" : [\"<em style='color:red;'>\"],
        \"post_tags\" : [\"</em>\"],
        \"fields\" : {
            \"body\" : {}
        }
    }
}
"
