function  validationRib(rib) {

    var ribPart1;
    if (rib.substring(0, 1) === '0') {
        ribPart1 = rib.substring(1, 6);
    }
    else {
        ribPart1 = rib.substring(0, 6);
    }

    var ribPart2 = rib.substring(6, 12);
    var ribPart3 = rib.substring(12, 18);
    var ribPart4 = rib.substring(18, 22) + "00";

    var a = parseInt(ribPart1) % 97;
    var b = parseInt((a + '' + ribPart2)) % 97;
    var c = parseInt((b + '' + ribPart3)) % 97;
    var d = parseInt((c + '' + ribPart4)) % 97;

    var cle = 97 - d;
    if (cle === parseInt(rib.substring(22, 24))) {
        return 1;
    } else {
        return 0;
    }

}

function validationCnss(cnss) {

    var ch = cnss.substring(1, 8);
    var sommePairs = 0;
    var sommeImpairs = 0;
    var nbp = 0;
    var nbi = 0;
    var i = 0;
    var j = 1;
    for (; i < 8; ) {
        nbp = parseInt(ch.substring(i, i + 1));
        sommePairs = sommePairs + nbp;
        i = i + 2;
    }

    for (; j < 7; ) {
        nbi = parseInt(ch.substring(j, j + 1));
        sommeImpairs = sommeImpairs + nbi;
        j = j + 2;
    }
    var cle = 10 - (((sommePairs * 2) + sommeImpairs) % 10);
    if (cle === 10) {
        cle = 0;
    }
    if (cle === cnss.substring(8, 9)) {
        return 1;
    } else {
        return 0;
    }

}