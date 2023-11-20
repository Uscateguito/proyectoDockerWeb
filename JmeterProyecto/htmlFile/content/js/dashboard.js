/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [1.0, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "ObtenerListaGenerosUObjeto"], "isController": false}, {"data": [1.0, 500, 1500, "BorrarAdmin"], "isController": false}, {"data": [1.0, 500, 1500, "BorrarCancion"], "isController": false}, {"data": [1.0, 500, 1500, "BorrarPersona2"], "isController": false}, {"data": [1.0, 500, 1500, "AgregarNuevoAdmin"], "isController": false}, {"data": [1.0, 500, 1500, "ObtenerListaUObjetoCanciones"], "isController": false}, {"data": [1.0, 500, 1500, "ActualizarPersona"], "isController": false}, {"data": [1.0, 500, 1500, "ActualizarAdmin"], "isController": false}, {"data": [1.0, 500, 1500, "CrearCancion"], "isController": false}, {"data": [1.0, 500, 1500, "RelacionarPersonasYCanciones"], "isController": false}, {"data": [1.0, 500, 1500, "login"], "isController": false}, {"data": [1.0, 500, 1500, "CrearUnNuevoGenero"], "isController": false}, {"data": [1.0, 500, 1500, "ObtenerListaUObjetoPersona"], "isController": false}, {"data": [1.0, 500, 1500, "RelacionarPersonasyGeneros"], "isController": false}, {"data": [1.0, 500, 1500, "ModificarGenero"], "isController": false}, {"data": [1.0, 500, 1500, "Debug Sampler"], "isController": false}, {"data": [1.0, 500, 1500, "AgregarNuevaPersona"], "isController": false}, {"data": [1.0, 500, 1500, "BorrarGenero"], "isController": false}, {"data": [1.0, 500, 1500, "BorrarPersona"], "isController": false}, {"data": [1.0, 500, 1500, "RelacionarListayCancion"], "isController": false}, {"data": [1.0, 500, 1500, "ObtenerListauObjetoAdmins"], "isController": false}, {"data": [1.0, 500, 1500, "ModificarCancion"], "isController": false}, {"data": [1.0, 500, 1500, "Connect_Token"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 690, 0, 0.0, 25.19710144927535, 0, 271, 18.0, 44.799999999999955, 79.0, 84.09000000000003, 39.16893732970028, 22.16529672953565, 13.135475207198002], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["ObtenerListaGenerosUObjeto", 30, 0, 0.0, 13.999999999999998, 10, 22, 13.0, 18.0, 20.9, 22.0, 1.8248175182481752, 0.8887075159671532, 0.5595631843065693], "isController": false}, {"data": ["BorrarAdmin", 30, 0, 0.0, 17.03333333333333, 12, 29, 16.0, 22.0, 27.9, 29.0, 1.839587932303164, 0.6826595842531273, 0.5978061955788571], "isController": false}, {"data": ["BorrarCancion", 30, 0, 0.0, 18.96666666666666, 13, 42, 17.5, 23.0, 32.09999999999999, 42.0, 1.8347501681854321, 0.6808643202250627, 0.5996981262613907], "isController": false}, {"data": ["BorrarPersona2", 30, 0, 0.0, 15.233333333333333, 11, 26, 15.0, 19.900000000000002, 23.249999999999996, 26.0, 1.8422991893883567, 0.6836657148120855, 0.6023454771554901], "isController": false}, {"data": ["AgregarNuevoAdmin", 30, 0, 0.0, 16.366666666666664, 13, 30, 15.0, 20.0, 25.049999999999994, 30.0, 1.813894431344096, 1.3706357888929197, 0.6943814619989116], "isController": false}, {"data": ["ObtenerListaUObjetoCanciones", 30, 0, 0.0, 14.2, 10, 24, 13.0, 19.0, 21.249999999999996, 24.0, 1.8195050946142648, 0.9483104189410481, 0.5597110398471616], "isController": false}, {"data": ["ActualizarPersona", 30, 0, 0.0, 32.63333333333334, 21, 176, 27.0, 35.7, 106.1499999999999, 176.0, 1.7944730230888863, 1.448955186774734, 0.7900471236092834], "isController": false}, {"data": ["ActualizarAdmin", 30, 0, 0.0, 26.36666666666667, 21, 41, 26.0, 30.0, 38.25, 41.0, 1.814113805406059, 1.3743447458728912, 0.6869656867932515], "isController": false}, {"data": ["CrearCancion", 30, 0, 0.0, 15.466666666666667, 12, 24, 15.0, 19.800000000000004, 22.349999999999998, 24.0, 1.8174107954201246, 0.9418944425092384, 0.7791438859271823], "isController": false}, {"data": ["RelacionarPersonasYCanciones", 30, 0, 0.0, 27.5, 21, 49, 26.0, 33.0, 41.29999999999999, 49.0, 1.8248175182481752, 1.2032984527068125, 0.6300134960462287], "isController": false}, {"data": ["login", 30, 0, 0.0, 83.8, 74, 233, 78.5, 83.9, 153.2499999999999, 233.0, 1.765432825280998, 0.9913319087271231, 0.4706671497087036], "isController": false}, {"data": ["CrearUnNuevoGenero", 30, 0, 0.0, 16.366666666666667, 12, 28, 15.5, 21.900000000000002, 27.45, 28.0, 1.8207197912241306, 0.8813777348728531, 0.6472089882867027], "isController": false}, {"data": ["ObtenerListaUObjetoPersona", 30, 0, 0.0, 13.4, 10, 29, 12.5, 16.0, 21.84999999999999, 29.0, 1.8123602972270887, 1.0231694217060352, 0.5575131773696611], "isController": false}, {"data": ["RelacionarPersonasyGeneros", 30, 0, 0.0, 27.7, 22, 53, 26.5, 32.900000000000006, 44.19999999999999, 53.0, 1.8275967103259214, 1.1016148530307646, 0.6291882424611636], "isController": false}, {"data": ["ModificarGenero", 30, 0, 0.0, 28.500000000000007, 21, 45, 25.5, 38.900000000000006, 43.9, 45.0, 1.8210513536481727, 0.8833166087471167, 0.6396798553781716], "isController": false}, {"data": ["Debug Sampler", 30, 0, 0.0, 0.10000000000000005, 0, 1, 0.0, 0.9000000000000021, 1.0, 1.0, 1.8446781036709095, 1.5069074548053862, 0.0], "isController": false}, {"data": ["AgregarNuevaPersona", 30, 0, 0.0, 19.500000000000004, 14, 78, 17.0, 23.700000000000006, 49.94999999999996, 78.0, 1.7884821747943245, 1.4511041082925955, 0.8051662915822105], "isController": false}, {"data": ["BorrarGenero", 30, 0, 0.0, 12.333333333333332, 9, 27, 11.0, 17.700000000000006, 22.599999999999994, 27.0, 1.838235294117647, 0.6821576286764706, 0.5990421070772058], "isController": false}, {"data": ["BorrarPersona", 30, 0, 0.0, 17.23333333333333, 13, 32, 16.0, 22.900000000000002, 27.599999999999994, 32.0, 1.840942562592047, 0.6831622790868925, 0.6019618503006873], "isController": false}, {"data": ["RelacionarListayCancion", 30, 0, 0.0, 35.59999999999999, 28, 54, 36.0, 40.900000000000006, 49.05, 54.0, 1.8302727106338843, 1.0659908638887194, 0.6298711945579892], "isController": false}, {"data": ["ObtenerListauObjetoAdmins", 30, 0, 0.0, 12.533333333333333, 9, 22, 12.0, 16.0, 18.699999999999996, 22.0, 1.8166404263049534, 1.2063036476625895, 0.555281692806104], "isController": false}, {"data": ["ModificarCancion", 30, 0, 0.0, 27.999999999999996, 20, 61, 25.0, 35.900000000000006, 47.249999999999986, 61.0, 1.8174107954201246, 0.9436692577391409, 0.8087833002665535], "isController": false}, {"data": ["Connect_Token", 30, 0, 0.0, 86.7, 77, 271, 80.0, 84.9, 169.2499999999999, 271.0, 1.7457084666860634, 0.9802562190864127, 0.5608770366598779], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 690, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
