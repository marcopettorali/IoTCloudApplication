#include <stdio.h>

#include "cJSON.h"

int main() {
    // create main document
    cJSON* person = cJSON_CreateObject();

    // add simple objects to a document
    cJSON_AddStringToObject(person, "name", "Marco");
    cJSON_AddStringToObject(person, "surname", "Pettorali");
    cJSON_AddNumberToObject(person, "age", 23);

    // create a JSON array (as a list of cJSON) and add it to the document
    cJSON* array = cJSON_CreateArray();
    for (int i = 0; i < 10; i++) {
        cJSON* elem = cJSON_CreateNumber(i);
        cJSON_AddItemToArray(array, elem);
    }
    cJSON_AddItemToObject(person, "array", array);

    // extract the JSON string
    char* buffer = cJSON_Print(person);
    printf("json object : %s\n", buffer);

    // don't forget to delete the document!
    cJSON_Delete(person);

    // parse a JSON string
    cJSON* received_person = cJSON_Parse(buffer);
    if (!received_person) {
        printf("Impossible to parse!\n");
        return -1;
    }

    // get all the fields contained in the JSON string
    cJSON* name = cJSON_GetObjectItemCaseSensitive(received_person, "name");
    cJSON* surname = cJSON_GetObjectItemCaseSensitive(received_person, "surname");
    cJSON* age = cJSON_GetObjectItemCaseSensitive(received_person, "age");

    // get the C values of the JSON objects and print the final result
    printf("Hi, I'm %s %s, and I'm %d years old.\n", name->valuestring, surname->valuestring, age->valueint);

    // parse the array and print it (use cJSON_ArrayForEach() macro since it's more efficient)
    cJSON* iterable_list = cJSON_GetObjectItemCaseSensitive(received_person, "array");
    cJSON* cursor = NULL;
    cJSON_ArrayForEach(cursor, iterable_list){
        printf("%d\n", cursor->valueint);
    }

    return 0;
}