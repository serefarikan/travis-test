#!/usr/bin/env bash

echo 'installing third party jars to maven repository'

./install_3rd_party_jars.sh | tee install_jars.output

if grep -Fq "FAIL" install_jars.output ; then
    echo "installation of external jars failed"
    exit 1
else
    echo "Installation of jars succeeded"
fi
echo 'finished installing third party jars'