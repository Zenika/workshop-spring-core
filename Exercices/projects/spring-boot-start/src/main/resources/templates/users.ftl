<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Hello Spring Boot</title>

    <!--
        Those CSS files are delivered by Spring Boot
        They are located in src/main/resources/static.
        This folder is automatically exposed by par Spring Boot.
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
            <!-- Display the 'message' variable from the model -->
            <h1>${message}</h1>

            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login</th>
                        <th>Password</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over the list of users (loaded from the model) -->
                    <#list users as user>
                    <!-- Display a User -->
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