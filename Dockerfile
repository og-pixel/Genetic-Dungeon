FROM openjdk
RUN apt-get update && apt-get install -y --no-install-recommends openjfx && rm -rf /var/lib/apt/lists/*
COPY out /out 
WORKDIR /out/production/Genetic-Map
CMD ["java", "GMaps", "-Xss128m"]

