<div style="height:24.7cm; position: relative; border: 1px solid black;">
    <h1 style="position:absolute; top: 13%; width:100%; text-align: center;">{Titre-Formation}</h1>
    <h1 style="position:absolute; top: 33%; width:100%; text-align: center;"><img src="ressources/logo_elk.png" alt="ELK"></h1>
    <h1 style="position:absolute; top: 50%; width:100%; text-align: center;">Travaux Pratiques</h1>
    <img src="ressources/logo-zenika-small.png" style="position: absolute; bottom: 20px; right: 20px; height: 150px;">
</div>
<div class="pb"></div>

L'objectif des TPs sera de monitorer une application web fournie. Celle-ci
produit 2 types de logs sous forme de fichier:
  - Access Logs: leur format est très proche des access logs Apache
  - Logs applicatifs: ils sont produits avec la librarie Java Log4J

Pour réaliser les TPs, récupérer auprès du formateur:
  - Eventuellement, Java 1.8U20 ou plus récent
  - Elasticsearch 5.x
  - LogStash 5.x
  - Kibana 5.x
  - L'application Web: `formation-elk-webapp.jar`
  - Pour Windows, cUrl
  - Les ressources et solutions des TPs
  - Eventuellement Gatling 2.1.x, pour simuler de la charge et des Logs

<div class="pb"></div>
