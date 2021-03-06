var gameTagClearArr = [];

function insertPost() {
  var boardType = $("#boardType").val();
  var boardSubType = $("#boardSubType").val();
  var title = $("#title").val();
  var content = $("#content").val();
  var writerId = $("#writerId").val();
  var postType;

  if (boardType == "free" && boardSubType == "free") {
    postType = "잡담";
  }

  $.ajax({
    type: "POST",
    url: "http://localhost:8000/public/board/post",
    contentType: "application/json",
    traditional: true,
    data: JSON.stringify({
      boardType: boardType,
      boardSubType: boardSubType,
      title: title,
      content: content,
      writerId: writerId,
      postType: postType,
      gameTagList: gameTagClearArr,
    }),
    xhrFields: {
      withCredentials: true,
    },
    success: function () {
      goList();
    },
    error: function (request, status, error) {
      console.log(
        "code:" +
          request.status +
          "message:" +
          request.responseText +
          "error:" +
          error
      );
    },
  });
}

var ajaxLastRequest = 0;
var beforeGameTag;

function gameTagSearch() {
  var currentAjaxRequest;

  $(".gameTagSearch").html("");

  var gameTag = $("#inputGameTag").val();

  if (gameTag == beforeGameTag) return;
  beforeGameTag = gameTag;

  console.log(gameTag);

  var regIdPattern = /^[a-zA-Z0-9가-힣 ]{1,30}$/;

  var isValid = regIdPattern.test(gameTag);

  if (!isValid) return;

  $.ajax({
    dataType: "xml",
    type: "GET",
    url: "http://www.grac.or.kr/WebService/GameSearchSvc.asmx/game",
    data: {
      gametitle: gameTag,
      entname: "",
      rateno: "",
      display: "10",
      pageno: "1",
    },
    beforeSend: function (request) {
      ajaxLastRequest++;
      currentAjaxRequest = ajaxLastRequest;
    },
    success: function (result) {
      if (ajaxLastRequest != currentAjaxRequest) return;

      var str = "";

      $(result)
        .find("item")
        .each(function () {
          console.log($(this).find("gametitle").text());

          str += '<a href="#" class="list-group-item">';
          str += $(this).find("gametitle").text();
          str += "</a>";
        });

      $(".gameTagSearch").html(str);

      $(".list-group-item").on("click", function (event) {
        var searchedGameTag = $(this).text();

        if (gameTagClearArr.indexOf(searchedGameTag) == -1)
          // 이전에 선택한 적이 없다면
          gameTagClearArr.push(searchedGameTag);

        addGameTagClear();

        $(".gameTagSearch").html("");
      });

      beforeGameTag = "";
    },
    error: function (request, status, error) {
      console.log(
        "code:" +
          request.status +
          "message:" +
          request.responseText +
          "error:" +
          error
      );
    },
  });

  $("#inputGameTag").on("blur", function () {
    setTimeout(function () {
      $(".gameTagSearch").html("");
    }, 500);
  });
}

function addGameTagClear() {
  var str = "";

  for (var i = 0; i < gameTagClearArr.length; i++) {
    str += '<div style="margin: 10px">';
    str += '<span class="label label-info">' + gameTagClearArr[i] + "</span>";
    str +=
      '<button type="button" class="deleteGameTag close" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    str += "</div>";
  }

  $(".gameTagClear").html(str);

  $(".deleteGameTag").on("click", function (event) {
    var deletedGameTag = $(this).closest("span").text();
    gameTagClearArr.splice(gameTagClearArr.indexOf(deletedGameTag), 1);

    $(this).parent().remove();
  });
}

function goList() {
  var boardCriteriaFormGoList = $("#boardCriteriaFormGoList");

  boardCriteriaFormGoList.submit();
}

$(document).ready(function () {
  $("#inputGameTag").on("keyup", function () {
    setTimeout(function () {
      gameTagSearch();
    }, 500);
  });

  $("#register").on("click", function () {
    insertPost();
  });
});
