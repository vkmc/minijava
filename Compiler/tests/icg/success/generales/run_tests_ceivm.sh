#! /bin/bash
FAILURE=1
SUCCESS=0

CURRENT_DIR=`pwd`

for file in `ls`; do
	if [ ${file: -4} == ".txt" ]; then
		echo "${CURRENT_DIR}/$file"
		java -jar CeIVM-cei2011.jar "${CURRENT_DIR}/$file"
                printf "\n\n"
                #echo $file
	fi
done

if [ $? -ne 0 ]
then
	echo "Tests failed"
	exit ${FAILURE}
fi

echo "Tests succeded"
exit ${SUCCESS}
