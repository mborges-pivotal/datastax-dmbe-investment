#!/bin/bash
export KEYSPACE=stargate
export TABLE=trades_by_a_d


$DSBULK_BIN_PATH/dsbulk count -b $PATH_TO_BUNDLE -k $KEYSPACE -t $TABLE -u $USER_NAME -p $PASSWORD
