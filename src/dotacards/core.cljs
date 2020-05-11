(ns dotacards.core
  (:require
    [clojure.string :as string]
    [garden.core :as garden]
    [garden.color :as garden.color]))

(enable-console-print!)

(def prime?
  #{2 3 5 7 11 13 17 19 23 29 31
    37 41 43 47 53 59 61 67 71 73
    79 83 89 97 101 103 107 109 113})

(defn rank->nimmt-penalty [rank]
  (let [divisible? (fn [n div]
                     (= 0 (mod n div)))]
    (cond
      (and (divisible? rank 5) (divisible? rank 11))
      7
      (divisible? rank 11)
      5
      (divisible? rank 10)
      3
      (divisible? rank 5)
      2
      (prime? rank)
      0
      :else
      1)))

(defn hero->image [hero]
  (when hero
    (str "/hero_icons/" (string/replace hero #" " "_") ".png")))

(def color-ref
  [
   [:invisibility
    #{;dark
      "Doom"
      "Shadow Shaman"
      "Ursa"
      "Terrorblade"
      "Chaos Knight"
      "Abaddon"
      }
    #{"Nyx Assassin"}
    #{;black-red
      "Axe"
      "Batrider"
      "Beastmaster"
      "Shadow Demon"
      "Bloodseeker"
      "Dragon Knight"
      "Shadow Fiend"
      "Warlock"
      }
    ]

   [:haste
    #{;yellow-red-orange
      "Ember Spirit"
      "Mars"
      }
    #{"Grimstroke"}

    #{;beige-pink
      "Anti-Mage"
      "Enchantress"
      "Monkey King"
      "Pudge"
      "Lina"
      "Centaur Warrunner"
      "Kunkka"
      "Lycan"
      "Omniknight"
      "Dark Willow"
      "Invoker"
      }

    #{"Windranger"}
    ]

   [:illusion
    #{
      "Chen"
      "Skywrath Mage"
      "Troll Warlord"
      "Clinkz"
      "Alchemist"
      "Sand King"
      "Treant Protector"
      "Earthshaker"
      "Phoenix"
      "Bristleback"
      "Bounty Hunter"
      "Legion Commander"
      "Naga Siren"
      }
    #{"Earth Spirit"}
    #{"Oracle"}
    ]

   [:regeneration
    #{;green-yellow
      "Elder Titan"
      "Underlord"
      "Medusa"
      "Wraith King"
      "Venomancer"
      "Huskar"
      }
    #{;green-blck
      "Outworld Devourer"
      "Rubick"
      "Tidehunter"
      "Pugna"
      "Viper"
      }
    #{;weird-green
      "Undying"
      "Death Prophet"
      "Necrophos"
      }
    #{"Gyrocopter"}
    ]

   [:double-damage
    #{
      "Winter Wyvern"
      "Storm Spirit"
      "Visage"
      "Morphling"
      "Leshrac"
      "Spirit Breaker"
      "Phantom Assassin"
      "Weaver"
      "Nature's Prophet"
      }
     #{;light-blue
      "Queen of Pain"
      "Phantom Lancer"
      "Ancient Apparition"
      "Io2"
      "Lich"
      }
    #{"Drow Ranger"}
    ]

   [:bounty
    #{
      "Tinker"
      "Ogre Magi"
      "Jakiro"
      "Disruptor"
      "Snapfire"
      "Crystal Maiden"
      "Luna"
      "Templar Assassin"
      "Meepo"
      "Slark"
      "Pangolier"
      }
    #{;blue
      "Arc Warden"
      "Night Stalker"
      "Puck"
      }
    #{"Razor"}
    ]

   [:arcane
    #{
      "Bane"
      "Witch Doctor"
      "Faceless Void"
      "Enigma"
      "Riki"
      "Dark Seer"
      "Void Spirit"
      "Spectre"
      "Dazzle"
      "Lion"
      "Slardar"
      "Vengeful Spirit"
      "Silencer"
      "Broodmother"
      }
    #{"Timbersaw"}
    ]

   [:ancient
    #{
      "Mirana"
      "Sniper"
      "Tusk"
      "Juggernaut"
      "Zeus"
      "Sven"
      "Tiny"
      "Techies"
      "Clockwerk"
      "Magnus"
      "Lone Druid"}
    #{; beige
      "Keeper of the Light"
      "Lifestealer"
      "Brewmaster"
      }
    #{#_"Creep" #_"Animal Courier" "Roshan"}
    ]
   ])

(defn color-help-view []
  [:div
   (for [[group & hero-groups] color-ref]
     [:div.group {:style {:align-items "center"}}
      [:span {:style {:font-size "1.5em"
                      :display "inline-block"
                      :width "30px"}}
       (count (apply concat hero-groups))
       [:img {:src (str "/rune_icons/" (name group) ".png")
              :width "32px"
              :height "32px"}] ]
      (for [heroes hero-groups]
        (for [hero heroes]
          [:img {:src (hero->image hero)
                 :style {:width "64px"
                         :height "64px"
                         :image-rendering "pixelated"}}]))])])

(def themes
  {:dark {:text "#FFF"
          :fill "#000"}
   :light {:text ""
           :fill "#fff"}
   :color {:text "#000"
           :fill ""}})

(def suits
  [
   {:id :haste
    :color {:light {:text "#e63317"}
            :dark {:text "#ec331c"}
            :color {:fill "#e14f36"}}}
   {:id :bounty
    :color {:light {:text "#e38008"}
            :dark {:text "#e38008"}
            :color {:fill "#e38008"}}}
   {:id :illusion
    :color {:light {:text "#f6aa18"}
            :dark {:text "#f7c341"}
            :color {:fill "#f6aa18"}}}
   {:id :regeneration
    :color {:light {:text "#379500"}
            :dark {:text "#5afd00"}
            :color {:fill "#488b06"}}}
   {:id :double-damage
    :color {:light {:text "#2e56be"}
            :dark {:text "#499eff"}
            :color {:fill "#56aefe"}}}
   {:id :arcane
    :color {:light {:text "#a33bb9"}
            :dark {:text "#f45cf7"}
            :color {:fill "#b744c7"}}}
   {:id :invisibility
    :color {:light {:text "#300f53"}
            :dark {:text "#965db4"}
            :color {:fill "#2c1e38"
                    :text "#ac68cf"}}}
   {:id :ancient
    :color {:light {:text "#858782"}
            :dark {:text "#e6e6e6"}
            :color {:fill "#ffffff"}}}])

(def ranks
  [1 2 3 4 5 6 7 8 9 10 :J :Q :K :A :X])

(def deck
  (let [hero-groups (->> color-ref
                         (map (fn [[x & groups]]
                                [x (apply concat groups)]))
                         (into {}))]
    (->> (for [suit suits
               rank ranks]
           [suit rank])
         (map-indexed vector)
         (map (fn [[index [suit rank]]]
                (let [suit-index (->> (map :id suits)
                                      (map-indexed vector)
                                      (filter (fn [[_ x]]
                                                (= x (suit :id))))
                                      first
                                      first)]
                  {:id (str (suit :id) rank)
                   :rank rank
                   :suit suit
                   :global-rank (inc index) #_(if (int? rank)
                                  (+ (* suit-index
                                        10)
                                     rank)
                                  (+ 80 suit-index
                                     (get {:X 0
                                           :J 8
                                           :Q 16
                                           :K 24
                                           :A 32} rank)))
                   :hero (nth (hero-groups (suit :id))
                              (dec (get {:J 11
                                         :Q 12
                                         :K 13
                                         :A 14
                                         :X 15} rank rank)) "X") #_(nth (hero-groups (suit :id)) index)}))))))

(defn pips [n hero]
  (let [layout (cond
                 (= hero "Creep")
                 [{:top "50%"
                   :left "50%"
                   :width "88px"
                   :height "88px"}]
                 (= hero "Animal Courier")
                 [{:top "50%"
                   :left "50%"
                   :width "144px"
                   :height "144px"}]
                 :else
                 [{:top "50%"
                   :left "50%"
                   :width "128px"
                   :height "128px"}])
        #_(case n
            1 [{:top "50%"
                :left "50%"
                :width "64px"
                :height "64px"}]
            2 [{:bottom "30%"
                :left "50%"}
               {:top "30%"
                :left "50%"}]
            3 [{:bottom "25%"
                :left "50%"}
               {:top "50%"
                :left "50%"}
               {:top "25%"
                :left "50%"}]
            4 [{:bottom "30%"
                :left "50%"}
               {:top "30%"
                :left "50%"}
               {:top "50%"
                :left "0%"}
               {:top "50%"
                :right "0%"}]
            5 [{:bottom "30%"
                :left "0%"}
               {:top "30%"
                :left "0%"}
               {:top "30%"
                :right "0%"}
               {:bottom "30%"
                :right "0%"}
               {:bottom "50%"
                :right "50%"}]
            6 [{:bottom "25%"
                :left "0%"}
               {:top "25%"
                :left "0%"}
               {:top "25%"
                :right "0%"}
               {:bottom "25%"
                :right "0%"}
               {:bottom "50%"
                :right "0%"}
               {:bottom "50%"
                :left "0%"}]
            8 [{:bottom "25%"
                :left "0%"}
               {:top "25%"
                :left "0%"}
               {:top "25%"
                :right "0%"}
               {:bottom "25%"
                :right "0%"}
               {:bottom "50%"
                :right "0%"}
               {:bottom "50%"
                :left "0%"}
               {:bottom "37.5%"
                :left "50%"}
               {:top "37.5%"
                :left "50%"}]
            10 [{:bottom "20%"
                 :left "0%"}
                {:top "20%"
                 :left "0%"}
                {:top "20%"
                 :right "0%"}
                {:bottom "20%"
                 :right "0%"}
                {:bottom "40%"
                 :left "0%"}
                {:top "40%"
                 :left "0%"}
                {:top "40%"
                 :right "0%"}
                {:bottom "40%"
                 :right "0%"}
                {:bottom "30%"
                 :right "50%"}
                {:top "30%"
                 :right "50%"}]
            ; else
            [{:top "50%"
              :left "50%"
              :width "128px"
              :height "128px"}]
            )]
    [:div.pips
     {:style {:height "100%"
              :margin "0 17mm"
              :position "relative"}}
     (into [:<>]
           (for [position layout]
             [:img
              {:src (hero->image hero)
               :style (merge {:width "32px"
                              :height "32px"
                              :position "absolute"
                              :image-rendering "pixelated"}
                             (->> position
                                  (map (fn [[k v]]
                                         (case k
                                           (:left :right :top :bottom)
                                           [k (str "calc(" v " - " (or (position :width) "32px") "/ 2)")]
                                           [k v])))
                                  (into {})))}]))]))

(def theme :color)

(defn cards-view []
  [:div.cards
   [:style
    (garden/css
      {}
      [:body
       {:margin 0
        :background "#CCC"
        :padding "2em"}]

      [:.cards
       {:display "flex"
        :flex-wrap "wrap"}]

      [:.card
       {:margin "5mm"
        :box-shadow "1px 1px 1px 0 #0007"}

       (for [suit suits]
         [(str "&." (name (suit :id)))
          {:background
           (let [bg-color (or (-> suit :color theme :fill)
                              (-> themes theme :fill))]
             (str "radial-gradient(circle, "
                  ;"linear-gradient(141deg, "
                  (-> bg-color
                      (garden.color/lighten 5)
                      #_(garden.color/desaturate 10)
                      (garden.color/as-hex))
                  " 0%, "
                  (-> bg-color
                      (garden.color/darken 10)
                      #_(garden.color/saturate 100)
                      (garden.color/as-hex))

                  " 100%)"
                  )
             #_bg-color
             )
           :color (or (-> suit :color theme :text)
                      (-> themes theme :text))}
          [:.nimmt-penalty
           [:>.pip
            {:background (or (-> suit :color theme :text)
                             (-> themes theme :text))}]]])

       [:.meta
        [:>svg
         {:margin-top "4px"
          :width "16px"
          :height "16px"}]]])]

   (for [card (take 120 deck)]
     ^{:key (card :id)}
     [:div.card
      {:class (-> card :suit :id)
       :style
       {:font-family "Roboto"
        :width "63.5mm"
        :height "88.9mm"
        :box-sizing "border-box"
        :border-radius "3mm"
        ;:transform "rotate(180deg)"
        :padding "5mm"}}

      [:div.pad
       {:style {:position "relative"
                :height "100%"}}

       [:div.nimmt {:style {:text-align "center"
                            :position "absolute"
                            :bottom "0.2rem"
                            :right 0
                            :display "flex"
                            :flex-direction "column"
                            :align-items "center"}}

        (let [penalty (rank->nimmt-penalty (card :global-rank))]
          [:div.nimmt-penalty
           {:style {:display "inline-flex"
                    :flex-wrap "wrap-reverse"
                    :width "2rem"
                    :margin "1mm 0"
                    :align-items "flex-start"
                    :justify-content "center"}}
           (repeat penalty
                   [:div.pip
                    {:style {:margin "0.5mm"
                             :width "2mm"
                             :height "2mm"
                             :border-radius "50%"}}])])

        [:div.global-rank
         {:style {:font-size "2em"
                  :font-weight "500"
                  :line-height "1em"}}
         (card :global-rank)] ]

       (into [:<>]
             (for [position [:top-left #_:bottom-right]]
               [:div.meta
                {:style (merge
                          {:position "absolute"
                           :text-align "center" }
                          (case position
                            :top-left {:top 0
                                        :left 0}
                            :bottom-right {:bottom 0
                                            :right 0
                                            :transform "rotate(180deg)"}))}
                [:div.rank
                 {:style (merge
                           {:font-size "2em"
                            :line-height "1em"
                            :margin-top "0.3rem"
                            :margin-bottom "0.2rem"
                            :font-weight "500"}
                           (when (= theme :light)
                             {:font-weight "800"
                              #_#_:-webkit-text-stroke "2px black"}))}
                 (card :rank)]
                [:img {:width "32px"
                       :height "32px"
                       :image-rendering "pixelated"
                       ;; TODO use secondary color here
                       :style {:filter "drop-shadow(0px 0px 8px #0004)"}
                       :src (str "/rune_icons/" (name (-> card :suit :id)) ".png")}]]))
       [pips (card :rank) (card :hero)]]])

   ;; reverse of card
   [:div.card
    {:style {:width "63.5mm" ; 2.5 in @ 300dpi = 750px
             :height "88.9mm" ; 3.5 in @ 300dpi = 1050px
             :box-sizing "border-box"
             :border-radius "3mm"
             :background "radial-gradient(circle, #343434 30%, #000000 100%)"
             :display "flex"
             :justify-content "center"
             :align-items "center"}}
    [:div {:style {:padding "10px"
                   #_#_:background "#000"}}
     [:img {:src "/logo2.png"
            :width "92px"
            :height "92px"
            :image-rendering "pixelated"}]]]])

(defn app-view []
  [:<>
   [color-help-view]
   [cards-view]])
