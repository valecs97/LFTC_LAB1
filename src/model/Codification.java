package model;

public enum Codification {
    identifier,
    constant,
    constWord{
        public String toString() {
            return "const";
        }
    },
    program,
    array,
    var,
    intWord{
        public String toString(){
            return "int";
        }
    },
    booleanWord{
        public String toString() {
            return "boolean";
        }
    },
    day,
    start,
    end,
    read,
    write,
    whileWord{
        public String toString() {
            return "while";
        }
     },
    charWord{
        public String toString(){
            return "charWord";
        }
    },
    doWord{
        public String toString(){
            return "charWord";
        }
    },
    ifWord{
        public String toString(){
            return "if";
        }
    },
    then,
    elseWord{
        public String toString(){
            return "elseWord";
        }
    },
    and,
    or,
    not,
    pointComma{
        public String toString(){
            return ";";
        }
    },
    comma{
        public String toString(){
            return ",";
        }
    },
    plus{
        public String toString(){
            return "+";
        }
    },
    minus{
        public String toString(){
            return "-";
        }
    },
    times{
        public String toString(){
            return "*";
        }
    },
    slash{
        public String toString(){
            return "/";
        }
    },
    parenthesisOpen{
        public String toString(){
            return "(";
        }
    },
    parenthesisClose{
        public String toString(){
            return ")";
        }
    },
    sqaureBrachetsOpen{
        public String toString(){
            return "[";
        }
    },
    sqaureBrachetsClose{
        public String toString(){
            return "]";
        }
    },
    smaller{
        public String toString(){
            return "<";
        }
    },
    bigger{
        public String toString(){
            return ">";
        }
    },
    biggerOrEqual{
        public String toString(){
            return ">=";
        }
    },
    smallerOrEqual{
        public String toString(){
            return "<=";
        }
    },
    doubleEqual{
        public String toString(){
            return "==";
        }
    },
    equal{
        public String toString(){
            return "=";
        }
    },
    brachetsOpen{
        public String toString(){
            return "{";
        }
    },
    brachetsClose{
        public String toString(){
            return "}";
        }
    },
    notEqual{
        public String toString(){
            return "!=";
        }
    }
}
