#!/usr/bin/env bash

echo 'installing third party jars to maven repository'

./install_3rd_party_jars.sh | tee install_jars.output

if grep -Fq "FAIL" install_jars.output ; then
    echo "Installation of jars succeeded"
else
    exit 1
fi
echo 'finished installing third party jars'