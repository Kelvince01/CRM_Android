<script src="mraid.js"></script>
<script>
  var vaststring = `
  $$$vast$$$
  `;
  (function () {
    var settings = {
      "autoStart": true,
      "muted": true,
      "type": "rewarded",
      "advertising": {
        "tag":
            [{
              "url": vaststring,
              "client": "vast",
              "type": "xml"
            }]
      }
    };
    var exposedPercentageBeforeLoad;

    function load() {
      var api;
      var oldViewable;

      function show() {
        mraid.addEventListener("exposureChange", showAndPlay);
        mraid.removeEventListener("exposureChange", saveExposedPercentageBeforeLoad);
        exposedPercentageBeforeLoad && showAndPlay(exposedPercentageBeforeLoad);
      }

      function showAndPlay(exposedPercentage) {
        var viewable = exposedPercentage >= 50;
        if (oldViewable === viewable || oldViewable === undefined && !viewable) {
          return;
        }

        if (api === undefined) {
          create();
        } else if (viewable) {
          api.resumeAd();
        } else {
          api.pauseAd();
        }
        oldViewable = viewable;
      }

      function create() {
        var div = document.createElement("div");
        document.body.appendChild(div);

        api = AdPlayerPro(div);
        api.on("AdStopped", function () {
          mraid.removeEventListener("exposureChange", showAndPlay);
          mraid.close();
        });
        api.setup(settings);
      }

      function ready() {
        mraid.removeEventListener("ready", ready);
        show();
      }

      mraid.getState() === "loading" ? mraid.addEventListener("ready", ready) : show();
    }

    var script = document.createElement("script");
    script.onload = load;
    script.src = "https://serving.stat-rock.com/player/smaato.js";
    document.body.appendChild(script);

    function saveExposedPercentageBeforeLoad(exposedPercentage) {
      exposedPercentageBeforeLoad = exposedPercentage;
    }
    mraid.addEventListener("exposureChange", saveExposedPercentageBeforeLoad);
  })();
</script>
