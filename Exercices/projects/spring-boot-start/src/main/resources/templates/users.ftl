<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Hello Spring Boot</title>

    <!--
        Ces fichiers CSS sont servis par Spring Boot
        Ils se trouvent dans src/main/resources/static.
        Ce répertoire est automatiquement exposé par Spring Boot.
    -->
    <link rel="stylesheet" href="<@spring.url '/styles/bootstrap/3.3.5/css/bootstrap.min.css' />" />
    <link rel="stylesheet" href="<@spring.url '/styles/bootstrap/3.3.5/css/bootstrap-theme.min.css' />" />
</head>
<body>

    <div class="container">

        <!-- nav bar -->
        <div class="row">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">Spring Boot</a>
                    </div>

                </div>
            </nav>
        </div>

        <div class="row">
            <!-- Affiche la variable 'message' mise dans le modèle -->
            <h1>${message}</h1>

            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login</th>
                        <th>Mot de passe</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Itère sur la liste d'utilisateurs (chargée dans le modèle) -->
                    <#list users as user>
                    <!-- Affiche un utilisateur -->
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.password}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>

    </div>

</body>
</html>