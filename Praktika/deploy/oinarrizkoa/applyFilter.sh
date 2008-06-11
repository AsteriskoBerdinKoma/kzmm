#!/bin/sh

if [ $# -lt 1 ] 
then
    echo ""
    echo "ERROREA: ImageMagick-eko filtro bat adierazi behar duzu."
    echo ""
    echo "         ADIBIDEZ, sh applyFilter.sh chop 3x3"
    exit 1
else
    TR=$PWD'/TR'
    TS=$PWD'/TS'
    FTR=$PWD'/TR_'
    FTS=$PWD'/TS_'
    TR_FILTER=$FTR$1
    TS_FILTER=$FTS$1
    for j in $PWD/*
    do
        if [ -d $j ]
	then
 	    if [ $j = $TR ]
	    then
		if [ -d $TR_FILTER ] 
		then
		    rm -r $TR_FILTER
	            mkdir $TR_FILTER
		else
		    mkdir $TR_FILTER
		fi
		echo -n 'Entrenamenduko irudiak filtratzen. Itxaron une batez...  '
		for i in $TR/*.pgm
		do
		    UNEKO_TR=`echo $i | awk '{s=split($1,a,"/"); print a[s]}'`
 		    convert $i -compress none -$1 $2 $3 $4 $TR_FILTER/$UNEKO_TR
		done
		echo 'Eginda.'
	    fi
	    if [ $j = $TS ]
	    then
		if [ -d $TS_FILTER ] 
		then
		    rm -r $TS_FILTER
		    mkdir $TS_FILTER
		else
		    mkdir $TS_FILTER
		fi
		echo -n 'Testeko irudiak filtratzen. Itxaron une batez...  '
		for k in $TS/*.pgm
		do
		    UNEKO_TS=`echo $k | awk '{s=split($1,a,"/"); print a[s]}'`
 		    #echo $k
		    convert $k -compress none -$1 $2 $3 $4 $TS_FILTER/$UNEKO_TS
		done
		echo 'Eginda.'
	    fi
	fi
    done
	
fi


