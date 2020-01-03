import psycopg2
import os
from dotenv import load_dotenv, find_dotenv

load_dotenv(find_dotenv())

DATABASE_USER = os.getenv("DATABASE_USER")
DATABASE_PASSWORD = os.getenv("DATABASE_PASSWORD")
DATABASE_HOST = os.getenv("DATABASE_HOST")
DATABASE_PORT = os.getenv("DATABASE_PORT")
DATABASE_NAME = os.getenv("DATABASE_NAME")

# Define global variable
connection = None
try:
    connection = psycopg2.connect(user=DATABASE_USER,
                                  password=DATABASE_PASSWORD,
                                  host=DATABASE_HOST,
                                  port=DATABASE_PORT,
                                  database=DATABASE_NAME)
    cursor = connection.cursor()
except (Exception, psycopg2.Error) as error:
    print('Error while connecting to PostgreSQL', error)
