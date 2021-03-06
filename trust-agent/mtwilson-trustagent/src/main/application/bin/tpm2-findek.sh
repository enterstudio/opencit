#!/bin/bash
# WARNING:
# *** do NOT use TABS for indentation, use SPACES
# *** TABS will cause errors in some linux distributions

if [[ $# < 1 || $# > 2 ]]; then
  echo -e "usage: \n  $0 <ektype>\n or\n  $0 <ektype> verbose"
  exit 2
fi

ekType=$1 #RSA, ECC
verbose=$2 #verbose
ekTypeHex=unknown
tmpFile=/tmp/persistentobject

case $ekType in
  "RSA") ekTypeHex=0x1;;
  "ECC") ekTypeHex=0x23;;
esac

echo -n "Find EK ($ekType:$ekTypeHex): "
if [[ $ekTypeHex == unknown ]]; then
  echo "failed: unknown type"
  exit 1
fi

rm -rf $tmpFile
tpm2_listpersistent > $tmpFile
if [[ $? != 0 ]];then
  echo "failed: unable to list persistent handles"
  exit 1
fi

if [[ $verbose == "verbose" ]]; then
  echo
  cat $tmpFile
fi

result=`grep -B2 "Type: $ekTypeHex" $tmpFile | grep -o "0x810100.." | head -n1`
if [ -z $result ]; then
  echo "failed"
  exit 1
fi

echo "done. Found @ $result"
