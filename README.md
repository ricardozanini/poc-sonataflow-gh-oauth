# How to Call the GitHub API With OAuth Tokens from a SonataFlow Workflow

In this example project you will learn how to invoke the GitHub API from your workflow application. 

Instead of configuring a fixed Bearer or PAT Token to the workflow app, you will propagate the `access_token` from the current logged user to the workflow, which will use this token to make invocations to the GitHub API.

## Setup

1. Create an account in [ngrok](https://ngrok.com/docs/getting-started/) and a new custom domain to facilitate your configuration later. This is required for GitHub OAuth flow to call back your local application.
2. Create a [new GitHub OAuth App](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app) and take notes of your `client_secret` and `client_id`. Use the custom domain you created in the first step as the URLs for your app. The callback URL is `https://your.domain.ngrok-free.app/callback`.
3. Create an `.env` file in the root of your `get-consent-web` project with the values `POC_SF_GH_CLIENT_ID` and `POC_SF_GH_CLIENT_SECRET` for your GitHub's client ID and Secret respectively. The web app will use this info to connect to GitHub servers.

## Running the Example

1. Start the workflow application

```shell
cd flow-gh
mvn clean quarkus:dev
```

2. Start ngrok in a separate terminal.

```shell
ngrok http http://localhost:9090 --url https://<your domain>.ngrok-free.app
```

3. Finally, in a new terminal start the web application to get the GitHub access token.

```shell
cd get-consent-web
mvn clean quarkus:dev
```

Just go to the domain you created with ngrok, and you should see the web application. Give consent to GitHub and try calling the workflow, you should see your data information. See the screencast below:

![demo screencast](docs/demo-screencast.mov)