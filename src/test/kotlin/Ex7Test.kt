import org.example.calculateSignalOnWire
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Ex7Test {
    @ParameterizedTest(name = "wire {0} should have the signal {1}")
    @CsvSource(
        "d, 72",
        "e, 507",
        "f, 492",
        "g, 114",
        "h, 65412",
        "i, 65079",
        "x, 123",
        "y, 456"
    )
    fun `given a set of instructions, the given wire must have the correct signal`(wire: String, expectedSignal: Int) {
        val instructions = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent()
        assertEquals(expectedSignal, calculateSignalOnWire(instructions, wire))
    }

    @ParameterizedTest(name = "wire {0} should have the signal {1}")
    @CsvSource(
        "a, 956",
        "aa, 221",
        "ab, 1021",
        "ac, 84",
        "ad, 65451",
        "ae, 937",
        "af, 2025",
        "ag, 680",
        "ah, 64855",
        "ai, 1345",
        "aj, 8161",
        "ak, 257",
        "al, 65278",
        "am, 7904",
        "an, 0",
        "ao, 0",
        "ap, 0",
        "aq, 3536",
        "ar, 0",
        "as, 3536",
        "at, 884",
        "au, 442",
        "av, 110",
        "aw, 510",
        "ax, 42",
        "ay, 65493",
        "az, 468",
        "b, 14146",
        "ba, 1012",
        "bb, 340",
        "bc, 65195",
        "bd, 672",
        "be, 4080",
        "bf, 128",
        "bg, 65407",
        "bh, 3952",
        "bi, 0",
        "bj, 0",
        "bk, 0",
        "bl, 1768",
        "bm, 0",
        "bn, 1768",
        "bo, 442",
        "bp, 221",
        "bq, 55",
        "br, 255",
        "bs, 21",
        "bt, 65514",
        "bu, 234",
        "bv, 506",
        "bw, 170",
        "bx, 65365",
        "by, 336",
        "bz, 2040",
        "c, 0",
        "ca, 64",
        "cb, 65471",
        "cc, 1976",
        "cd, 0",
        "ce, 0",
        "cf, 0",
        "cg, 884",
        "ch, 0",
        "ci, 884",
        "cj, 221",
        "ck, 110",
        "cl, 27",
        "cm, 127",
        "cn, 10",
        "co, 65525",
        "cp, 117",
        "cq, 253",
        "cr, 85",
        "cs, 65450",
        "ct, 168",
        "cu, 1020",
        "cv, 32",
        "cw, 65503",
        "cx, 988",
        "cy, 0",
        "cz, 0",
        "d, 3536",
        "da, 0",
        "db, 442",
        "dc, 0",
        "dd, 442",
        "de, 110",
        "df, 55",
        "dg, 13",
        "dh, 63",
        "di, 5",
        "dj, 65530",
        "dk, 58",
        "dl, 126",
        "dm, 42",
        "dn, 65493",
        "do, 84",
        "dp, 510",
        "dq, 16",
        "dr, 65519",
        "ds, 494",
        "dt, 0",
        "du, 0",
        "dv, 0",
        "dw, 221",
        "dx, 0",
        "dy, 221",
        "dz, 55",
        "e, 1768",
        "ea, 27",
        "eb, 6",
        "ec, 31",
        "ed, 2",
        "ee, 65533",
        "ef, 29",
        "eg, 63",
        "eh, 21",
        "ei, 65514",
        "ej, 42",
        "ek, 255",
        "el, 8",
        "em, 65527",
        "en, 247",
        "eo, 1",
        "ep, 0",
        "eq, 1",
        "er, 110",
        "es, 32768",
        "et, 32878",
        "eu, 8219",
        "ev, 4109",
        "ew, 1027",
        "ex, 5135",
        "ey, 1",
        "ez, 65534",
        "f, 442",
        "fa, 5134",
        "fb, 13343",
        "fc, 10",
        "fd, 65525",
        "fe, 13333",
        "ff, 46207",
        "fg, 4",
        "fh, 65531",
        "fi, 46203",
        "fj, 1",
        "fk, 2",
        "fl, 3",
        "fm, 16439",
        "fn, 32768",
        "fo, 49207",
        "fp, 12301",
        "fq, 6150",
        "fr, 1537",
        "fs, 7687",
        "ft, 0",
        "fu, 65535",
        "fv, 7687",
        "fw, 15887",
        "fx, 4101",
        "fy, 61434",
        "fz, 11786",
        "g, 2042",
        "ga, 60991",
        "gb, 2",
        "gc, 65533",
        "gd, 60989",
        "ge, 1",
        "gf, 6",
        "gg, 7",
        "gh, 24603",
        "gi, 32768",
        "gj, 57371",
        "gk, 14342",
        "gl, 7171",
        "gm, 1792",
        "gn, 7939",
        "go, 1024",
        "gp, 64511",
        "gq, 6915",
        "gr, 15111",
        "gs, 6146",
        "gt, 59389",
        "gu, 8965",
        "gv, 58143",
        "gw, 8193",
        "gx, 57342",
        "gy, 49950",
        "gz, 0",
        "h, 168",
        "ha, 14",
        "hb, 14",
        "hc, 28685",
        "hd, 0",
        "he, 28685",
        "hf, 7171",
        "hg, 3585",
        "hh, 896",
        "hi, 3969",
        "hj, 512",
        "hk, 65023",
        "hl, 3457",
        "hm, 7555",
        "hn, 3073",
        "ho, 62462",
        "hp, 4482",
        "hq, 29071",
        "hr, 4096",
        "hs, 61439",
        "ht, 24975",
        "hu, 1",
        "hv, 28",
        "hw, 29",
        "hx, 14342",
        "hy, 32768",
        "hz, 47110",
        "i, 65367",
        "ia, 11777",
        "ib, 5888",
        "ic, 1472",
        "id, 6080",
        "ie, 1280",
        "if, 64255",
        "ig, 4800",
        "ih, 16065",
        "ii, 512",
        "ij, 65023",
        "ik, 15553",
        "il, 48327",
        "im, 14336",
        "in, 51199",
        "io, 33991",
        "ip, 1",
        "iq, 58",
        "ir, 59",
        "is, 23555",
        "it, 32768",
        "iu, 56323",
        "iv, 14080",
        "iw, 7040",
        "ix, 1760",
        "iy, 8160",
        "iz, 640",
        "j, 1874",
        "ja, 64895",
        "jb, 7520",
        "jc, 16224",
        "jd, 5376",
        "je, 60159",
        "jf, 10848",
        "jg, 65123",
        "jh, 2048",
        "ji, 63487",
        "jj, 63075",
        "jk, 1",
        "jl, 118",
        "jm, 119",
        "jn, 28161",
        "jo, 32768",
        "jp, 60929",
        "jq, 15232",
        "jr, 7616",
        "js, 1904",
        "jt, 8176",
        "ju, 1344",
        "jv, 64191",
        "jw, 6832",
        "jx, 15280",
        "jy, 6784",
        "jz, 58751",
        "k, 4050",
        "ka, 8496",
        "kb, 61233",
        "kc, 8192",
        "kd, 57343",
        "ke, 53041",
        "kf, 1",
        "kg, 238",
        "kh, 239",
        "ki, 30464",
        "kj, 32768",
        "kk, 63232",
        "kl, 15808",
        "km, 7904",
        "kn, 1976",
        "ko, 8184",
        "kp, 1696",
        "kq, 63839",
        "kr, 6488",
        "ks, 15832",
        "kt, 6464",
        "ku, 59071",
        "kv, 9368",
        "kw, 63384",
        "kx, 9216",
        "ky, 56319",
        "kz, 54168",
        "l, 1360",
        "la, 0",
        "lb, 478",
        "lc, 478",
        "ld, 31616",
        "le, 0",
        "lf, 31616",
        "lg, 7904",
        "lh, 3952",
        "li, 988",
        "lj, 4092",
        "lk, 848",
        "ll, 64687",
        "lm, 3244",
        "ln, 7916",
        "lo, 3232",
        "lp, 62303",
        "lq, 4684",
        "lr, 31692",
        "ls, 4608",
        "lt, 60927",
        "lu, 27084",
        "lv, 0",
        "lw, 956",
        "lx, 956",
        "ly, 15808",
        "lz, 0",
        "m, 64175",
        "ma, 15808",
        "n, 2690",
        "o, 16322",
        "p, 514",
        "q, 65021",
        "r, 15808",
        "s, 0",
        "t, 0",
        "u, 0",
        "v, 7073",
        "w, 0",
        "x, 7073",
        "y, 1768",
        "z, 884"
    )
    fun `given a set of instructions 2, the given wire must have the correct signal`(
        wire: String,
        expectedSignal: Int
    ) {
        val instructions = """
            af AND ah -> ai
            NOT lk -> ll
            hz RSHIFT 1 -> is
            NOT go -> gp
            du OR dt -> dv
            x RSHIFT 5 -> aa
            at OR az -> ba
            eo LSHIFT 15 -> es
            ci OR ct -> cu
            b RSHIFT 5 -> f
            fm OR fn -> fo
            NOT ag -> ah
            v OR w -> x
            g AND i -> j
            an LSHIFT 15 -> ar
            1 AND cx -> cy
            jq AND jw -> jy
            iu RSHIFT 5 -> ix
            gl AND gm -> go
            NOT bw -> bx
            jp RSHIFT 3 -> jr
            hg AND hh -> hj
            bv AND bx -> by
            er OR es -> et
            kl OR kr -> ks
            et RSHIFT 1 -> fm
            e AND f -> h
            u LSHIFT 1 -> ao
            he RSHIFT 1 -> hx
            eg AND ei -> ej
            bo AND bu -> bw
            dz OR ef -> eg
            dy RSHIFT 3 -> ea
            gl OR gm -> gn
            da LSHIFT 1 -> du
            au OR av -> aw
            gj OR gu -> gv
            eu OR fa -> fb
            lg OR lm -> ln
            e OR f -> g
            NOT dm -> dn
            NOT l -> m
            aq OR ar -> as
            gj RSHIFT 5 -> gm
            hm AND ho -> hp
            ge LSHIFT 15 -> gi
            jp RSHIFT 1 -> ki
            hg OR hh -> hi
            lc LSHIFT 1 -> lw
            km OR kn -> ko
            eq LSHIFT 1 -> fk
            1 AND am -> an
            gj RSHIFT 1 -> hc
            aj AND al -> am
            gj AND gu -> gw
            ko AND kq -> kr
            ha OR gz -> hb
            bn OR by -> bz
            iv OR jb -> jc
            NOT ac -> ad
            bo OR bu -> bv
            d AND j -> l
            bk LSHIFT 1 -> ce
            de OR dk -> dl
            dd RSHIFT 1 -> dw
            hz AND ik -> im
            NOT jd -> je
            fo RSHIFT 2 -> fp
            hb LSHIFT 1 -> hv
            lf RSHIFT 2 -> lg
            gj RSHIFT 3 -> gl
            ki OR kj -> kk
            NOT ak -> al
            ld OR le -> lf
            ci RSHIFT 3 -> ck
            1 AND cc -> cd
            NOT kx -> ky
            fp OR fv -> fw
            ev AND ew -> ey
            dt LSHIFT 15 -> dx
            NOT ax -> ay
            bp AND bq -> bs
            NOT ii -> ij
            ci AND ct -> cv
            iq OR ip -> ir
            x RSHIFT 2 -> y
            fq OR fr -> fs
            bn RSHIFT 5 -> bq
            0 -> c
            14146 -> b
            d OR j -> k
            z OR aa -> ab
            gf OR ge -> gg
            df OR dg -> dh
            NOT hj -> hk
            NOT di -> dj
            fj LSHIFT 15 -> fn
            lf RSHIFT 1 -> ly
            b AND n -> p
            jq OR jw -> jx
            gn AND gp -> gq
            x RSHIFT 1 -> aq
            ex AND ez -> fa
            NOT fc -> fd
            bj OR bi -> bk
            as RSHIFT 5 -> av
            hu LSHIFT 15 -> hy
            NOT gs -> gt
            fs AND fu -> fv
            dh AND dj -> dk
            bz AND cb -> cc
            dy RSHIFT 1 -> er
            hc OR hd -> he
            fo OR fz -> ga
            t OR s -> u
            b RSHIFT 2 -> d
            NOT jy -> jz
            hz RSHIFT 2 -> ia
            kk AND kv -> kx
            ga AND gc -> gd
            fl LSHIFT 1 -> gf
            bn AND by -> ca
            NOT hr -> hs
            NOT bs -> bt
            lf RSHIFT 3 -> lh
            au AND av -> ax
            1 AND gd -> ge
            jr OR js -> jt
            fw AND fy -> fz
            NOT iz -> ja
            c LSHIFT 1 -> t
            dy RSHIFT 5 -> eb
            bp OR bq -> br
            NOT h -> i
            1 AND ds -> dt
            ab AND ad -> ae
            ap LSHIFT 1 -> bj
            br AND bt -> bu
            NOT ca -> cb
            NOT el -> em
            s LSHIFT 15 -> w
            gk OR gq -> gr
            ff AND fh -> fi
            kf LSHIFT 15 -> kj
            fp AND fv -> fx
            lh OR li -> lj
            bn RSHIFT 3 -> bp
            jp OR ka -> kb
            lw OR lv -> lx
            iy AND ja -> jb
            dy OR ej -> ek
            1 AND bh -> bi
            NOT kt -> ku
            ao OR an -> ap
            ia AND ig -> ii
            NOT ey -> ez
            bn RSHIFT 1 -> cg
            fk OR fj -> fl
            ce OR cd -> cf
            eu AND fa -> fc
            kg OR kf -> kh
            jr AND js -> ju
            iu RSHIFT 3 -> iw
            df AND dg -> di
            dl AND dn -> do
            la LSHIFT 15 -> le
            fo RSHIFT 1 -> gh
            NOT gw -> gx
            NOT gb -> gc
            ir LSHIFT 1 -> jl
            x AND ai -> ak
            he RSHIFT 5 -> hh
            1 AND lu -> lv
            NOT ft -> fu
            gh OR gi -> gj
            lf RSHIFT 5 -> li
            x RSHIFT 3 -> z
            b RSHIFT 3 -> e
            he RSHIFT 2 -> hf
            NOT fx -> fy
            jt AND jv -> jw
            hx OR hy -> hz
            jp AND ka -> kc
            fb AND fd -> fe
            hz OR ik -> il
            ci RSHIFT 1 -> db
            fo AND fz -> gb
            fq AND fr -> ft
            gj RSHIFT 2 -> gk
            cg OR ch -> ci
            cd LSHIFT 15 -> ch
            jm LSHIFT 1 -> kg
            ih AND ij -> ik
            fo RSHIFT 3 -> fq
            fo RSHIFT 5 -> fr
            1 AND fi -> fj
            1 AND kz -> la
            iu AND jf -> jh
            cq AND cs -> ct
            dv LSHIFT 1 -> ep
            hf OR hl -> hm
            km AND kn -> kp
            de AND dk -> dm
            dd RSHIFT 5 -> dg
            NOT lo -> lp
            NOT ju -> jv
            NOT fg -> fh
            cm AND co -> cp
            ea AND eb -> ed
            dd RSHIFT 3 -> df
            gr AND gt -> gu
            ep OR eo -> eq
            cj AND cp -> cr
            lf OR lq -> lr
            gg LSHIFT 1 -> ha
            et RSHIFT 2 -> eu
            NOT jh -> ji
            ek AND em -> en
            jk LSHIFT 15 -> jo
            ia OR ig -> ih
            gv AND gx -> gy
            et AND fe -> fg
            lh AND li -> lk
            1 AND io -> ip
            kb AND kd -> ke
            kk RSHIFT 5 -> kn
            id AND if -> ig
            NOT ls -> lt
            dw OR dx -> dy
            dd AND do -> dq
            lf AND lq -> ls
            NOT kc -> kd
            dy AND ej -> el
            1 AND ke -> kf
            et OR fe -> ff
            hz RSHIFT 5 -> ic
            dd OR do -> dp
            cj OR cp -> cq
            NOT dq -> dr
            kk RSHIFT 1 -> ld
            jg AND ji -> jj
            he OR hp -> hq
            hi AND hk -> hl
            dp AND dr -> ds
            dz AND ef -> eh
            hz RSHIFT 3 -> ib
            db OR dc -> dd
            hw LSHIFT 1 -> iq
            he AND hp -> hr
            NOT cr -> cs
            lg AND lm -> lo
            hv OR hu -> hw
            il AND in -> io
            NOT eh -> ei
            gz LSHIFT 15 -> hd
            gk AND gq -> gs
            1 AND en -> eo
            NOT kp -> kq
            et RSHIFT 5 -> ew
            lj AND ll -> lm
            he RSHIFT 3 -> hg
            et RSHIFT 3 -> ev
            as AND bd -> bf
            cu AND cw -> cx
            jx AND jz -> ka
            b OR n -> o
            be AND bg -> bh
            1 AND ht -> hu
            1 AND gy -> gz
            NOT hn -> ho
            ck OR cl -> cm
            ec AND ee -> ef
            lv LSHIFT 15 -> lz
            ks AND ku -> kv
            NOT ie -> if
            hf AND hl -> hn
            1 AND r -> s
            ib AND ic -> ie
            hq AND hs -> ht
            y AND ae -> ag
            NOT ed -> ee
            bi LSHIFT 15 -> bm
            dy RSHIFT 2 -> dz
            ci RSHIFT 2 -> cj
            NOT bf -> bg
            NOT im -> in
            ev OR ew -> ex
            ib OR ic -> id
            bn RSHIFT 2 -> bo
            dd RSHIFT 2 -> de
            bl OR bm -> bn
            as RSHIFT 1 -> bl
            ea OR eb -> ec
            ln AND lp -> lq
            kk RSHIFT 3 -> km
            is OR it -> iu
            iu RSHIFT 2 -> iv
            as OR bd -> be
            ip LSHIFT 15 -> it
            iw OR ix -> iy
            kk RSHIFT 2 -> kl
            NOT bb -> bc
            ci RSHIFT 5 -> cl
            ly OR lz -> ma
            z AND aa -> ac
            iu RSHIFT 1 -> jn
            cy LSHIFT 15 -> dc
            cf LSHIFT 1 -> cz
            as RSHIFT 3 -> au
            cz OR cy -> da
            kw AND ky -> kz
            lx -> a
            iw AND ix -> iz
            lr AND lt -> lu
            jp RSHIFT 5 -> js
            aw AND ay -> az
            jc AND je -> jf
            lb OR la -> lc
            NOT cn -> co
            kh LSHIFT 1 -> lb
            1 AND jj -> jk
            y OR ae -> af
            ck AND cl -> cn
            kk OR kv -> kw
            NOT cv -> cw
            kl AND kr -> kt
            iu OR jf -> jg
            at AND az -> bb
            jp RSHIFT 2 -> jq
            iv AND jb -> jd
            jn OR jo -> jp
            x OR ai -> aj
            ba AND bc -> bd
            jl OR jk -> jm
            b RSHIFT 1 -> v
            o AND q -> r
            NOT p -> q
            k AND m -> n
            as RSHIFT 2 -> at
            """.trimIndent()
        assertEquals(expectedSignal, calculateSignalOnWire(instructions, wire))
    }
}