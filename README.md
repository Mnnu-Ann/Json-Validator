# Json-Validator

object:   {} / { members }

members:  pair / pair , members

pair:     string : value

array :   [] / [ elements ]

elements: value / value , elements

value:    number / object / array / true / false / null/ string 

string:   "" / " char " / " chars "

number:   digit/digits/fractions/exponents


----------------------------------------------------------------------------------------------


class Validator

    - main --> matchValidate


class ParseException


class Validate

    - matchValidate --> matchObject OR matchArray

    - matchObject --> matchMember

    - matchMember ---> matchPair

    - matchPair --> matchValue

    - matchArray --> matchValue

    - matchValue --> matchNumber OR matchString OR matchObject OR matchArray

        OR matchTrue OR matchTrue OR matchNull 

    - matchNull

    - matchFalse

    - matchTrue

    - matchString

    - matchNumber






note::
   '-' implies methods
   '-->' implies call to other method
