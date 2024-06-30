# Multi-arch build with Paketo Buildpacks

## JVM

### Echo

Step 1: ARM64

```shell
pack build ghcr.io/thomasvitale/echo-java-arm64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=false \
    --env BP_SPRING_AOT_ENABLED=true \
    --path echo \
    --platform linux/arm64 \
    --report-output-dir ./report-echo-arm64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 2: AMD64

```shell
pack build ghcr.io/thomasvitale/echo-java-amd64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=false \
    --env BP_SPRING_AOT_ENABLED=true \
    --path echo \
    --platform linux/amd64 \
    --report-output-dir ./report-echo-amd64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 3

```shell
docker buildx imagetools create -t ghcr.io/thomasvitale/echo-java \
  ghcr.io/thomasvitale/echo-java-arm64 \
  ghcr.io/thomasvitale/echo-java-amd64
```

## Native

### Result

Step 1: ARM64

```shell
pack build ghcr.io/thomasvitale/result-java-arm64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path result \
    --platform linux/arm64 \
    --report-output-dir ./report-result-arm64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 2: AMD64

```shell
pack build ghcr.io/thomasvitale/result-java-amd64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path result \
    --platform linux/amd64 \
    --report-output-dir ./report-result-amd64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 3

```shell
docker buildx imagetools create -t ghcr.io/thomasvitale/result-java \
  ghcr.io/thomasvitale/result-java-arm64 \
  ghcr.io/thomasvitale/result-java-amd64
```

### Vote

Step 1: ARM64

```shell
pack build ghcr.io/thomasvitale/vote-java-arm64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path vote \
    --platform linux/arm64 \
    --report-output-dir ./report-vote-arm64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 2: AMD64

```shell
pack build ghcr.io/thomasvitale/vote-java-amd64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path vote \
    --platform linux/amd64 \
    --report-output-dir ./report-vote-amd64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 3

```shell
docker buildx imagetools create -t ghcr.io/thomasvitale/vote-java \
  ghcr.io/thomasvitale/vote-java-arm64 \
  ghcr.io/thomasvitale/vote-java-amd64
```

### Worker

Step 1: ARM64

```shell
pack build ghcr.io/thomasvitale/worker-java-arm64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path worker \
    --platform linux/arm64 \
    --report-output-dir ./report-worker-arm64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 2: AMD64

```shell
pack build ghcr.io/thomasvitale/worker-java-amd64 \
    --builder docker.io/paketobuildpacks/builder-jammy-buildpackless-tiny \
    --buildpack gcr.io/paketo-buildpacks/java-native-image \
    --env BP_JVM_VERSION=21 \
    --env BP_NATIVE_IMAGE=true \
    --path worker \
    --platform linux/amd64 \
    --report-output-dir ./report-worker-amd64.toml \
    --volume $HOME/.m2:/home/cnb/.m2:rw \
    --publish
```

Step 3

```shell
docker buildx imagetools create -t ghcr.io/thomasvitale/worker-java \
  ghcr.io/thomasvitale/worker-java-arm64 \
  ghcr.io/thomasvitale/worker-java-amd64
```
