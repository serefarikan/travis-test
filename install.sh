#!/usr/bin/env bash

echo 'installing third party jars to maven repository'

./install_3rd_party_jars.sh | tee install_jars.output
grep -Fq "FAIL" install_jars.output
if [$?]
then
exit 1
fi
echo 'finished installing third party jars'