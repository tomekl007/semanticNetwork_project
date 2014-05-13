/*if (document.addEventListener) {
    document.addEventListener("DOMContentLoaded", init, false);
} else {
    window.onload = init;

} */

/*$( "#btnUndoNodeDeletions" ).click(function() {
    console.log( "Handler for .click() called." );
});*/
function refreshButtonHandler(){
    //$('#sigmaElement').empty();
    //getForServicesData();

   // window.location.reload();
    init();
}

//setInterval(function(){window.location.reload();},3000);

var ignoreNextMouseUp = false;
var nodeOutgoingEdges = {};
var thisGraphControl;

    var name,
    nickname  ,
    data = {};


function getForServicesData(){
    name = document.getElementById("name").value;
    nickname = document.getElementById("nickname").value;
    data["fullName"] = name;
    data["nickName"] = nickname;
    console.log("name -> " + name);


    $.ajax({
        type: "POST",
        data : JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: "http://localhost:8080/graphData",
        fileType: "JSON",
        success: function(data){
            console.log("get data: " + data);
            parseData(data);
        }
    });
}

function dropAllNodesAndEdges(graph, sigInst) {
    graph.nodes.forEach(function(current){
        console.log("drop : " + current.id);
        sigInst.dropNode(current.id);
    });
    graph.edges.forEach(function(current){
        sigInst.dropEdge(current.id);
    });

}
function parseData(data){
    console.log("  ->>>      "  +  JSON.stringify(data));

graph = {

                    nodes: [],
                    edges: []
                };

      for (var object in data.list){
                if (data.list.hasOwnProperty(object)){

                    o ={
                        id: data.list[object]["serviceName"],
                        label: data.list[object]["serviceName"], //+" [ "+ data.list[object]["env"] + " ] ", //+ " Environment: "+data.list[object]["environment"] ,
                        circular_x:  Math.cos(Math.PI * 2 * object / 10 - Math.PI / 2),
                        circular_y:  Math.sin(Math.PI * 2 * object / 10 - Math.PI / 2),
                        circular_size: 10,
                        circular_color: (data.list[object]["status"] == "ok") ? "#2EA717": "#E70101",
                        grid_x: 2 % 10,
                        grid_y: Math.floor(2 / 10),
                        grid_size: 1,
                        grid_color: "#333"



                    };

                    ['x', 'y', 'size', 'color'].forEach(function(val) {
                        o[val] = o['grid_' + val];
                    });

                    graph.nodes.push(o);


                   for (var edge in data.list[object]["dependecies"]){
                       if (data.list[object]['dependecies'].hasOwnProperty(edge)){

                        graph.edges.push({
                                   id: data.list[object]["serviceName"] + "->" + data.list[object]["dependecies"][edge] ,
                                   source: data.list[object]["serviceName"],
                                   target: data.list[object]["dependecies"][edge],
                                   color: "#646464",
                                    arrow: 'target'

                               });
                    }
                   }


                }

            }

    //addDummyNodes(graph);

    console.log("parsed graph:  " + JSON.stringify(graph));

    var sigInst = thisGraphControl.getSigInst();

    dropAllNodesAndEdges(graph, sigInst);


    drawNodesFromJson(graph);



    drawEdgesFromJson(graph, sigInst);


}


function drawEdgesFromJson(graph, sigInst){

    for (var i=0;i<graph.edges.length;i++)
    {
        sigInst.addEdge(graph.edges[i].id, graph.edges[i].source, graph.edges[i].target, {
            arrow: '' // include value here to override defaultEdgeArrow
        });
    }

}


function drawNodesFromJson(graph){

    var nextLine = 0;
    var y = 0;
    var x = 0;
    var counter = 0;
    for (var i=0;i<graph.nodes.length;i++)
    {

        console.log("nextLine : " + nextLine);
        var plusOrMinus = Math.random() < 0.2 ? -1 : 1;
        counter ++;
        if(nextLine != 0 && counter == 2) {
            y += 0.3;
            counter = 0;
        }
        if(nextLine == 0){
            x = 0;
        }
        else if( nextLine % 2 == 0){
            x = - 0.3;
        }else {
            x = 0.3;
        }

        console.log("x i y : " + x + " , " + y);
        console.log("draw node : " + JSON.stringify(graph.nodes[i]));
        thisGraphControl.addNodeToSigIns(graph.nodes[i].id,
            graph.nodes[i].label,
            graph.nodes[i].circular_color,
            x,y,
            //graph.nodes[0].grid_x,
            //graph.nodes[0].grid_y,
            graph.nodes[i].size
        );

        nextLine ++;
    }
}


function init() {

    getForServicesData();


    $('#sigmaElement').mousemove(function(e) {
        if (e.which === 1 && !ignoreNextMouseUp) {
            ignoreNextMouseUp = true;
        }
    });

    setupGraphControl('sigmaElement');

    setNodeCount();
    setArrowType();
    setEdgeType();
    setEdgeSize();

    //thisGraphControl.populate();
}

function setupGraphControl(inElementId) {

    var thisElement = document.getElementById(inElementId);
    if (thisElement) {
        thisElement.parentNode.style.display = 'inline-block';


               thisGraphControl = new graphControl();
               thisGraphControl.initialize(document.getElementById(inElementId));



    }
}

function graphControl() {
    var saveCoordinates = {};
    var sigInst;
    var id = 1;
    var activeNodeCount;

    this.initialize = function(inElement) {
        sigInst = sigma.init(inElement);

        sigInst.drawingProperties({
            defaultLabelColor: '#222',
            defaultLabelSize: 14,
            defaultLabelHoverColor: '#000',
            labelThreshold: 6,
            font: 'Arial',
            edgeColor: 'source',
            defaultEdgeType: 'curve',
            defaultEdgeArrow: 'target'
        }).graphProperties({
                minNodeSize: 10,
                maxNodeSize: 20,
                minEdgeSize: 5,
                maxEdgeSize: 5
            }).mouseProperties({
                maxRatio: 450,
                minRatio: .1,
                marginRatio: 1,
                zoomDelta: 0.1,
                dragDelta: 0.3,
                zoomMultiply: 1.5,
                inertia: 1.1
            });

        var greyColor = '#ccc';
        sigInst.bind('overnodes', function(event) {
            console.log("event  : " +event);
            var nodes = event.content;
            var neighbors = {};
            sigInst.iterEdges(function(e) {
                if (nodes.indexOf(e.source) < 0 && nodes.indexOf(e.target) < 0) {
                    if (!e.attr['grey']) {
                        e.attr['true_color'] = e.color;
                        e.color = greyColor;
                        e.attr['grey'] = 1;
                    }
                } else {
                    e.color = e.attr['grey'] ? e.attr['true_color'] : e.color;
                    e.attr['grey'] = 0;

                    neighbors[e.source] = 1;
                    neighbors[e.target] = 1;
                }
            }).iterNodes(function(n) {
                    if (!neighbors[n.id]) {
                        if (!n.attr['grey']) {
                            n.attr['true_color'] = n.color;
                            n.color = greyColor;
                            n.attr['grey'] = 1;
                        }
                    } else {
                        n.color = n.attr['grey'] ? n.attr['true_color'] : n.color;
                        n.attr['grey'] = 0;
                    }
                }).draw(2, 2, 2);
        }).bind('outnodes', function() {
                resetColor();
            }).bind('upnodes', function(event) {
                if (ignoreNextMouseUp) {
                    ignoreNextMouseUp = false;
                } else {


                }
            });
    };

    this.populate = function(inResetCoordintes) {
        if (inResetCoordintes) {
            saveCoordinates = {};
        }

        id = 1;
        nodeOutgoingEdges = {};
        sigInst.emptyGraph();

        switch (activeNodeCount) {
            case 'Sparse':
                addChildNodes(addNode(1), 2, 1, function(nodeId) {
                    addChildNodes(nodeId, 3, 1);
                });
                break;
            case 'Abundant':
                addChildNodes(addNode(1), 2, 5, function(nodeId) {
                    addChildNodes(nodeId, 3, 5, function(nodeId) {
                        addChildNodes(nodeId, 4, 3);
                    });
                });
                break;
            default:
                // Moderate
                addChildNodes(addNode(1), 2, 3, function(nodeId) {
                    addChildNodes(nodeId, 3, 2);
                });
        }

        sigInst.draw();



    };

    this.getSigInst = function(){
        return sigInst;
    }

    this.draw = function() {
        sigInst.draw();
    };

    this.setNodeCount = function(inNodeCount) {
        activeNodeCount = inNodeCount;
    };

    this.setArrowType = function(inArrowType) {
        sigInst.drawingProperties('defaultEdgeArrow', inArrowType);
    };

    this.setEdgeType = function(inEdgeType) {
        sigInst.drawingProperties('defaultEdgeType', inEdgeType);
    };

    this.setEdgeSize = function(inEdgeSize) {
        switch (inEdgeSize) {
            case 'thin':
                sigInst.graphProperties('minEdgeSize', 3);
                sigInst.graphProperties('maxEdgeSize', 3);
                break;
            case 'medium':
                sigInst.graphProperties('minEdgeSize', 5);
                sigInst.graphProperties('maxEdgeSize', 5);
                break;
            case 'thick':
                sigInst.graphProperties('minEdgeSize', 9);
                sigInst.graphProperties('maxEdgeSize', 9);
        }
    };

    var resetColor = function() {
        sigInst.iterEdges(function(e) {
            e.color = e.attr['grey'] ? e.attr['true_color'] : e.color;
            e.attr['grey'] = 0;
        }).iterNodes(function(n) {
                n.color = n.attr['grey'] ? n.attr['true_color'] : n.color;
                n.attr['grey'] = 0;
            }).draw(2, 2, 2);
    };

    this.recursiveNodeDrop = function(inNodeId) {
        console.log("drop recursivly : " + inNodeId);
        if (nodeOutgoingEdges[inNodeId]) {
            var thisEdge;
            for (var i = 0; i < nodeOutgoingEdges[inNodeId].length; i++) {
                try {
                    thisEdge = sigInst.getEdges(nodeOutgoingEdges[inNodeId][i]);
                    console.log("thisEdge : " + thisEdge);
                } catch (err) {}

                if (thisEdge) {
                    recursiveNodeDrop(thisEdge['target']);
                }
            }

            delete nodeOutgoingEdges[inNodeId];
        }

        //    console.log(sigInst.getNodes(inNodeId));

        sigInst.dropNode(inNodeId);
    };

    this.addNodeToSigIns = function(nodeId, label, color, cord_x, cord_y, size){
        sigInst.addNode(nodeId, {
            label: label,
            color: color,
            x: cord_x,
            y: cord_y,
            size: size
        });
    };

    addNode = function(inLevel, inParentNodeId) {
        var color = 'hsl(185, 10%, 42%)';
        var size = 1;

        switch (inLevel) {
            case 1:
                color = 'navy';
                break;
            case 2:
                color = 'maroon';
                break;
            case 3:
                color = '#51a351';
                break;
            default:
                //color = 'black';
                size = 0;
        }

        var nodeId = getNextId();
        var thisCoordinates = getCoordinates(nodeId);

        sigInst.addNode(nodeId, {
            label: nodeId,
            color: color,
            x: thisCoordinates.x,
            y: thisCoordinates.y,
            size: size
        });

        if (inParentNodeId !== undefined) {
            var edgeId = getNextId();

            if (nodeOutgoingEdges[inParentNodeId]) {
                nodeOutgoingEdges[inParentNodeId].push(edgeId);
            } else {
                nodeOutgoingEdges[inParentNodeId] = [edgeId];
            }

            sigInst.addEdge(edgeId, inParentNodeId, nodeId, {
                arrow: '' // include value here to override defaultEdgeArrow
            });
        }

        return nodeId;
    };

    var addChildNodes = function(inParentNodeId, inLevel, inChildCount, inFct) {
        var nodeId;
        for (var i = 0; i < inChildCount; i++) {
            nodeId = addNode(inLevel, inParentNodeId);
            if (inFct) {
                inFct(nodeId);
            }
        }
    };

    var getNextId = function() {
        return id++;
    };

    var getCoordinates = function(inId) {
        var returnCoordinates = saveCoordinates[inId];

        if (!returnCoordinates) {
            returnCoordinates = {
                x: Math.random(),
                y: Math.random()
            };

            saveCoordinates[inId] = returnCoordinates;
        }

        return returnCoordinates;
    };
}

function setNodeCount(inNodeCount) {
    if (!inNodeCount) {
        inNodeCount = "Moderate";
    }

    thisGraphControl.setNodeCount(inNodeCount);

    $('#btnNodeCount').html(inNodeCount + '&nbsp;&nbsp;<span class="caret"></span>');
}

function setArrowType(inArrowType) {
    if (!inArrowType) {
        inArrowType = "target";
    }

    thisGraphControl.setArrowType(inArrowType);

    switch (inArrowType) {
        case 'target':
            inArrowType = 'Target';
            break;
        case 'source':
            inArrowType = 'Source';
            break;
        case 'both':
            inArrowType = 'Both';
            break;
        case 'none':
            inArrowType = 'None';
    }

    $('#btnArrowType').html(inArrowType + '&nbsp;&nbsp;<span class="caret"></span>');
}

function setEdgeType(inEdgeType) {
    if (!inEdgeType) {
        inEdgeType = "Line";
    }

    thisGraphControl.setEdgeType(inEdgeType);

    switch (inEdgeType) {
        case 'curve':
            inEdgeType = 'Curve';
            break;
        case 'line':
            inEdgeType = 'Line';
    }

    $('#btnEdgeType').html(inEdgeType + '&nbsp;&nbsp;<span class="caret"></span>');
}

function setEdgeSize(inEdgeSize) {
    if (!inEdgeSize) {
        inEdgeSize = "medium";
    }

    thisGraphControl.setEdgeSize(inEdgeSize);

    switch (inEdgeSize) {
        case 'thin':
            inEdgeSize = 'Thin';
            break;
        case 'medium':
            inEdgeSize = 'Medium';
            break;
        case 'thick':
            inEdgeSize = 'Thick';
    }

    $('#btnEdgeSize').html(inEdgeSize + '&nbsp;&nbsp;<span class="caret"></span>');
}
